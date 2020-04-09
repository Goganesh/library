package ru.otus.library.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.library.model.Author;
import ru.otus.library.model.Book;
import ru.otus.library.model.Genre;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Dao JPA для работы с книгами")
@DataJpaTest
@Import({BookDaoJpa.class, AuthorDaoJpa.class, GenreDaoJpa.class})
class BookDaoJpaTest {

    @Autowired
    private BookDao bookDao;
    @Autowired
    private AuthorDao authorDao;
    @Autowired
    private GenreDao genreDao;

    @Autowired
    private TestEntityManager em;

    @DisplayName("сохранять книгу и связанные сущности")
    @Test
    void shouldSaveBook() {
        List<Genre> expectedGenres = new ArrayList<>();
        expectedGenres.add(genreDao.getGenreById(2L));
        Author expectedAuthor = authorDao.getAuthorById(1L);
        Book expectedBook = new Book("The Queen Margo", expectedAuthor, expectedGenres);
        long actualId = bookDao.saveBook(expectedBook);
        long expectedID = 4L;

        assertEquals(expectedID, actualId);
    }

    @DisplayName("возвращать книгу и связанные сущности по id")
    @Test
    void shouldReturnBookByIdWithAllInfo() {
        Book expectedBook = em.find(Book.class, 1L);
        Book actualBook = bookDao.getBookByIdWithAllInfo(1L);

        assertEquals(expectedBook, actualBook);
        assertNotNull(actualBook.getAuthor());
        assertNotNull(actualBook.getGenres());
    }

    @DisplayName("возвращать книгу и связанные сущности по имени")
    @Test
    void shouldReturnBookByNameWithAllInfo() {
        Book expectedBook = em.find(Book.class, 1L);
        Book actualBook = bookDao.getBookByNameWithAllInfo("The Three Musketeers");

        assertEquals(expectedBook, actualBook);
        assertNotNull(actualBook.getAuthor());
        assertNotNull(actualBook.getGenres());
    }

    @DisplayName("возвращать книги по автору")
    @Test
    void getAllBooksByAuthorWithAllInfo() {
        int expectedSize = 2;
        int actualSize = bookDao.getAllBooksByAuthorWithAllInfo(new Author(1L, "Alexandre Dumas")).size();

        assertEquals(expectedSize, actualSize);
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
        expectedGenres.add(new Genre(1L, "Drama"));
        expectedGenres.add(new Genre(2L,"Novel"));
        Author expectedAuthor = new Author(1L, "Alexandre Dumas");
        Book expectedBook = new Book(1L, "The Three Musketeers!!!", expectedAuthor, expectedGenres);

        bookDao.updateBook(expectedBook);
        Book actualBook = em.find(Book.class,1L);

        assertEquals(expectedBook, actualBook);
    }

    @DisplayName("удалять книгу по id")
    @Test
    void shouldDeleteBookById() {
        int expectedSize = 2;
        bookDao.deleteBookById(1L);
        int actualSize = bookDao.getAllBooksWithAllInfo().size();

        assertEquals(expectedSize, actualSize);
    }
}