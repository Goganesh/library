package ru.otus.library.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.model.Author;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Dao для работы с авторами")
@JdbcTest
@Import(AuthorDaoJdbc.class)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class AuthorDaoJdbcTest {
    private static final int EXPECTED_AUTHOR_SIZE = 2;

    @Autowired
    private AuthorDao authorDao;

    @DisplayName("генерить id при сохранении автора в БД")
    @Test
    void shouldGenerateIdForNewAuthor() {
        Author author = new Author("Basiladze Georgy");
        long actualId = authorDao.saveAuthor(author);
        long expectedId = 3L;

        assertEquals(expectedId, actualId);
    }

    @DisplayName("возвращать автора по его id")
    @Test
    void shouldReturnExpectedAuthorById() {
        Author actualAuthor = authorDao.getAuthorById(3L);
        Author expectedAuthor = new Author(3L, "Basiladze Georgy");

        assertEquals(expectedAuthor, actualAuthor);
    }

    @DisplayName("редактировать существующего автора")
    @Test
    void shouldUpdateExistAuthor() {
        String expectedName = "Basiladze David";
        authorDao.updateAuthor(new Author(1L, "Basiladze David"));
        String actualName = authorDao.getAuthorById(1L).getName();

        assertEquals(actualName, expectedName);
    }

    @DisplayName("удалять автора по id")
    @Test
    void shouldDeleteAuthorById() {
        int expectedSize = authorDao.getAllAuthors().size() - 1;
        authorDao.deleteAuthorById(2L);
        int actualSize = authorDao.getAllAuthors().size();

        assertEquals(expectedSize, actualSize);
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @DisplayName("возвращать всех авторов")
    @Test
    void shouldReturnAllAuthors() {
        int expectedSize = 2;
        int actualSize = authorDao.getAllAuthors().size();

        assertEquals(expectedSize, actualSize);
    }
}