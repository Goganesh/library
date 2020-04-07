package ru.otus.library.dao;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.library.model.Author;
import ru.otus.library.model.Book;
import ru.otus.library.model.Genre;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@AllArgsConstructor
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;

    private static Logger logger = LoggerFactory.getLogger(BookDaoJdbc.class);

    @Override
    public long saveBook(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", book.getName())
                .addValue("authorId", book.getAuthor().getId());
        jdbc.update("insert into books (name, author_id) values (:name, :authorId)", params, keyHolder);
        book.setId(keyHolder.getKey().longValue());
        insertGenreToBook(book);
        logger.info("save book - " + book.toString());

        return book.getId();
    }

    @Override
    public Book getBookByIdWithAllInfo(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        Map<Long, List<Genre>> bookToGenre = jdbc.query("select book_id, genre_id, genres.name from book_genre join genres on  book_genre.genre_id = genres.id" +
                        " where book_id = :id order by book_id",
                params, new BookDaoJdbc.BookToGenreExtractor());

        Book book = jdbc.queryForObject("select * from books join authors on books.author_id = authors.id where books.id = :id",
                params, new BookDaoJdbc.BookMapper());
        mapBookToGenre(book, bookToGenre);
        logger.info("book found - " + book.toString());

        return book;
    }

    @Override
    public List<Book> getAllBooksWithAllInfo() {
        Map<Long, List<Genre>> bookToGenre = jdbc.query("select book_id, genre_id, genres.name from book_genre join genres " +
                        "on book_genre.genre_id = genres.id order by book_id",
                new BookDaoJdbc.BookToGenreExtractor());
        List<Book> books = jdbc.query("select * from books join authors on books.author_id = authors.id",
                new BookDaoJdbc.BookMapper());
        mapBooksToGenre(books, bookToGenre);
        logger.info("all books - " + books.size());

        return books;
    }

    @Override
    public List<Book> getAllBooksByAuthorWithAllInfo(Author author) {
        Map<String, Object> params = Collections.singletonMap("authorId", author.getId());
        Map<Long, List<Genre>> bookToGenre = jdbc.query("select book_id, genre_id, genres.name from book_genre join genres on" +
                        " book_genre.genre_id = genres.id order by book_id",
                params, new BookDaoJdbc.BookToGenreExtractor());
        List<Book> books = jdbc.query("select * from books join authors on books.author_id = authors.id where author_id = :authorId",
                params, new BookDaoJdbc.BookMapper());
        mapBooksToGenre(books, bookToGenre);
        logger.info("all books by author - " + books.size());

        return books;
    }

    @Override
    public void updateBook(Book book) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", book.getId())
                .addValue("name", book.getName())
                .addValue("authorId", book.getAuthor().getId());
        jdbc.update("update books set name = :name, author_id = :authorId where id = :id", params);
        Book oldBook = getBookByIdWithAllInfo(book.getId());
        List<Long> oldGenresId = getIdGenreList(oldBook.getGenres());
        List<Long> newGenresId = getIdGenreList(book.getGenres());
        updateGenreLink(newGenresId, oldGenresId, book);

        logger.info("update book - " + book.toString());
    }

    @Override
    public void deleteBook(Book book) {
        deleteBookById(book.getId());
    }

    @Override
    public void deleteBookById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbc.update("delete from books where id = :id", params);
        logger.info("delete book by id - " + id);
    }

    private List<Long> getIdGenreList(List<Genre> genres) {
        List<Long> genresIds = new ArrayList<>();
        for(Genre genre : genres){
            genresIds.add(genre.getId());
        }
        return genresIds;
    }

    private void insertGenreToBook(Book book) {
        long bookId = book.getId();

        for(Genre genre : book.getGenres()){
            SqlParameterSource addParams = new MapSqlParameterSource()
                    .addValue("bookId", bookId)
                    .addValue("genreId", genre.getId());
            jdbc.update("insert into book_genre (book_id, genre_id) values (:bookId, :genreId)", addParams, new GeneratedKeyHolder());
        }
    }

    private void updateGenreLink(List<Long> newGenreId, List<Long> oldGenreID, Book book){
        List<Map <String, Object>> batchValues = new ArrayList<>();
        for(Long id : oldGenreID){
            batchValues.add(new MapSqlParameterSource("bookId", book.getId())
                            .addValue("genreId", id)
                            .getValues());
        }
        int[] count = jdbc.batchUpdate("delete from book_genre where book_id = :bookId and genre_id = :genreId",
                batchValues.toArray(new Map[batchValues.size()]));
        logger.info("batch delete size " + count.length);

        batchValues = new ArrayList<>();

        for(Long id : newGenreId){
            batchValues.add(new MapSqlParameterSource("bookId", book.getId())
                    .addValue("genreId", id)
                    .getValues());
        }
        count = jdbc.batchUpdate("insert into book_genre (book_id, genre_id) values (:bookId, :genreId)",
                batchValues.toArray(new Map[batchValues.size()]));
        logger.info("batch insert size " + count.length);
    }

    private void mapBooksToGenre(List<Book> books, Map<Long, List<Genre>> bookToGenre){
        for(Book book : books){
            mapBookToGenre(book, bookToGenre);
        }
    }

    private void mapBookToGenre(Book book, Map<Long, List<Genre>> bookToGenre){
        long bookId = book.getId();
        book.setGenres(bookToGenre.get(bookId));
    }

    @AllArgsConstructor
    private static class BookToGenreExtractor implements ResultSetExtractor<Map<Long, List<Genre>>> {

        @Override
        public Map<Long, List<Genre>> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            Map<Long, List<Genre>> bookToGenre = new LinkedHashMap<>();

            while (resultSet.next()){
                long bookId = resultSet.getLong("book_id");
                bookToGenre.putIfAbsent(bookId, new ArrayList<>());
                long genreId = resultSet.getLong("genre_id");
                String genreName = resultSet.getString("genres.name");
                Genre genre = new Genre(genreId, genreName);
                bookToGenre.get(bookId).add(genre);
            }

            return bookToGenre;
        }
    }

    @AllArgsConstructor
    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("books.id");
            String name = resultSet.getString("books.name");
            long authorId = resultSet.getLong("author_id");
            String authorName = resultSet.getString("authors.name");
            Author author = new Author(authorId, authorName);
            return new Book(id, name, author);
        }
    }
}
