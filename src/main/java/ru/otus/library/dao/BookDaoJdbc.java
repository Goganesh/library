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

@Repository("bookDao")
@AllArgsConstructor
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    private static Logger logger = LoggerFactory.getLogger(BookDaoJdbc.class);

    @Override
    public long saveBook(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", book.getName())
                .addValue("authorId", book.getAuthor().getId());
        jdbc.update("insert into books (name, authorId) values (:name, :authorId)", params, keyHolder);
        book.setId(keyHolder.getKey().longValue());
        long bookId = book.getId();

        for(Genre genre : book.getGenres()){
            SqlParameterSource addParams = new MapSqlParameterSource()
                    .addValue("bookId", bookId)
                    .addValue("genreId", genre.getId());
            jdbc.update("insert into bookToGenre (bookId, genreId) values (:bookId, :genreId)", addParams, new GeneratedKeyHolder());
        }
        logger.info("save book - " + book.toString());

        return bookId;
    }

    @Override
    public Book getBookById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);

        Map<Long, List<Genre>> bookToGenre = jdbc.query("select bookid, genreid from booktogenre where bookid = :id order by bookid",
                params, new BookDaoJdbc.BookExtractor(genreDao));

        Book book = jdbc.queryForObject("select * from books where id = :id", params, new BookDaoJdbc.BookMapper(authorDao));
        mapBookToGenre(book, bookToGenre);
        logger.info("book found - " + book.toString());

        return book;
    }


    @Override
    public List<Book> getAllBooks() {
        Map<Long, List<Genre>> bookToGenre = jdbc.query("select bookid, genreid from booktogenre order by bookid",
                new BookDaoJdbc.BookExtractor(genreDao));
        List<Book> books = jdbc.query("select * from books", new BookDaoJdbc.BookMapper(authorDao));

        mapBooksToGenre(books, bookToGenre);
        logger.info("all books - " + books.size());

        return books;
    }

    @Override
    public List<Book> getAllBooksByAuthor(Author author) {
        Map<String, Object> params = Collections.singletonMap("authorid", author.getId());
        Map<Long, List<Genre>> bookToGenre = jdbc.query("select bookid, genreid from booktogenre order by bookid",
                params, new BookDaoJdbc.BookExtractor(genreDao));
        List<Book> books = jdbc.query("select * from books where authorid = :authorid", params, new BookDaoJdbc.BookMapper(authorDao));

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

        jdbc.update("update books set name = :name, authorId = :authorId where id = :id", params);

        Map<String, Object> addParams = Collections.singletonMap("id", book.getId());
        List<Long> oldGenresId = jdbc.queryForList("select genreid from booktogenre where bookid = :id", addParams, Long.class);
        List<Genre> newGenres = book.getGenres();
        List<Long> newGenresId = new ArrayList<>();
        for(Genre genre : newGenres){
            newGenresId.add(genre.getId());
        }
        updateGenreLink(newGenresId, oldGenresId, book);

        logger.info("update book - " + book.toString());
    }

    private void updateGenreLink(List<Long> newGenreId, List<Long> oldGenreID, Book book){
        for(Long id : oldGenreID){
            if (!newGenreId.contains(id)){
                SqlParameterSource params = new MapSqlParameterSource()
                        .addValue("bookid", book.getId())
                        .addValue("genreid", id);
                jdbc.update("delete from booktogenre where bookid = :bookid and genreid = :genreid", params);
            }
        }
        for(Long id : newGenreId){
            if (!oldGenreID.contains(id)){
                SqlParameterSource params = new MapSqlParameterSource()
                        .addValue("bookid", book.getId())
                        .addValue("genreid", id);
                jdbc.update("insert into booktogenre (bookid, genreid) values (:bookid, :genreid)", params , new GeneratedKeyHolder());
            }
        }
    }

    @Override
    public void deleteBook(Book book) {
        deleteBookById(book.getId());
    }

    @Override
    public void deleteBookById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbc.update("delete from books where id = :id", params);
        jdbc.update("delete from booktogenre where bookid = :id", params);
        logger.info("delete book by id - " + id);
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
    private static class BookExtractor implements ResultSetExtractor<Map<Long, List<Genre>>> {
        private final GenreDao genreDao;

        @Override
        public Map<Long, List<Genre>> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            Map<Long, List<Genre>> bookToGenre = new LinkedHashMap<>();

            while (resultSet.next()){
                long bookId = resultSet.getLong("bookid");
                bookToGenre.putIfAbsent(bookId, new ArrayList<>());
                long genreId = resultSet.getLong("genreId");
                Genre genre = genreDao.getGenreById(genreId);
                bookToGenre.get(bookId).add(genre);
            }

            return bookToGenre;
        }
    }

    @AllArgsConstructor
    private static class BookMapper implements RowMapper<Book> {
        private final AuthorDao authorDao;

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            long authorId = resultSet.getLong("authorId");
            Author author = authorDao.getAuthorById(authorId);
            return new Book(id, name, author);
        }
    }
}