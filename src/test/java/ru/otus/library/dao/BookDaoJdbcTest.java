package ru.otus.library.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.model.Author;
import ru.otus.library.model.Book;
import ru.otus.library.model.Genre;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Dao JDBC для работы с книгами")
@JdbcTest
@Import({BookDaoJdbc.class, AuthorDaoJdbc.class, GenreDaoJdbc.class})
class BookDaoJdbcTest {

    @Autowired
    BookDao bookDao;

    @DisplayName("сохранять книгу и возвращай новый id")
    @Test
    void shouldSaveBook() {
        List<Genre> expectedGenres = new ArrayList<>();
        expectedGenres.add(new Genre(2L,"Novel"));
        Author expectedAuthor = new Author(1L, "Alexandre Dumas");
        Book expectedBook = new Book("The Queen Margo", expectedAuthor, expectedGenres);
        long actualId = bookDao.saveBook(expectedBook);
        long expectedID = 4L;

        assertEquals(expectedID, actualId);
    }

    @DisplayName("возвращать книгу и связанные сущности по id")
    @Test
    void shouldReturnBookById() {
        Book actualBook = bookDao.getBookByIdWithAllInfo(1L);
        List<Genre> expectedGenres = new ArrayList<>();
        expectedGenres.add(new Genre(2L,"Novel"));
        expectedGenres.add(new Genre(3L, "Comedy"));
        Author expectedAuthor = new Author(1L, "Alexandre Dumas");
        Book expectedBook = new Book(1L, "The Three Musketeers", expectedAuthor, expectedGenres);

        assertEquals(expectedBook, actualBook);
    }

    @DisplayName("возвращать все книги")
    @Test
    void shouldReturnAllBooks() {
        int expectedSize = 3;
        int actualSize = bookDao.getAllBooksWithAllInfo().size();

        assertEquals(expectedSize, actualSize);
    }

    @DisplayName("редактировать книгу")
    @Test
    void shouldUpdateBook() {
        List<Genre> expectedGenres = new ArrayList<>();
        expectedGenres.add(new Genre(2L,"Novel"));
        expectedGenres.add(new Genre(1L, "Drama"));
        Author expectedAuthor = new Author(1L, "Alexandre Dumas");
        Book expectedBook = new Book(1L, "The Three Musketeers!!!", expectedAuthor, expectedGenres);

        bookDao.updateBook(expectedBook);
        Book actualBook = bookDao.getBookByIdWithAllInfo(1L);

        assertEquals(expectedBook, actualBook);
    }

    @DisplayName("удалять книгу по id")
    @Test
    void shouldDeleteBookById() {
        int expectedSize = 3;
        bookDao.getBookByIdWithAllInfo(1L);
        int actualSize = bookDao.getAllBooksWithAllInfo().size();

        assertEquals(expectedSize, actualSize);
    }

    @DisplayName("возвращать книги по автору")
    @Test
    void shouldReturnBooksByAuthor() {
        int expectedSize = 2;
        int actualSize = bookDao.getAllBooksByAuthorWithAllInfo(new Author(1L, "Alexandre Dumas")).size();

        assertEquals(expectedSize, actualSize);
    }
}