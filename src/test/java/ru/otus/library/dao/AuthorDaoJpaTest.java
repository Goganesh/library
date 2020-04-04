package ru.otus.library.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.library.model.Author;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Dao JPA для работы с авторами")
@DataJpaTest
@Import(AuthorDaoJpa.class)
class AuthorDaoJpaTest {

    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private TestEntityManager em;

    @DisplayName("сохранять нового автора и возвращать id")
    @Test
    void shouldSaveNewAuthorAndReturnId() {
        Author expectedAuthor = new Author("Georgy Basiladze");
        long expectedId = authorDao.saveAuthor(expectedAuthor);
        expectedAuthor.setId(expectedId);
        Author actualAuthor = em.find(Author.class, expectedId);

        assertEquals(expectedAuthor, actualAuthor);
    }

    @DisplayName("возвращать автора по его id")
    @Test
    void shouldReturnExpectedAuthorById() {
        Author actualAuthor = authorDao.getAuthorById(1L);
        Author expectedAuthor = em.find(Author.class, 1L);

        assertEquals(expectedAuthor, actualAuthor);
    }

    @DisplayName("возвращать автора по имени")
    @Test
    void shouldReturnAuthorByName() {
        Author actualAuthor = authorDao.getAuthorByName("Alexandre Dumas");
        Author expectedAuthor = em.find(Author.class, 1L);

        assertEquals(expectedAuthor, actualAuthor);
    }

    @DisplayName("возвращать всех авторов")
    @Test
    void shouldReturnAllAuthors() {
        int expectedSize = 3;
        int actualSize = authorDao.getAllAuthors().size();

        assertEquals(expectedSize, actualSize);
    }

    @DisplayName("обновлять имя автора")
    @Test
    void shouldUpdateAuthorName() {
        String expectedAuthorName = "David Basiladze";
        Author actualAuthor = new Author(1L, expectedAuthorName);
        authorDao.updateAuthor(actualAuthor);
        String actualAuthorName = em.find(Author.class, 1L).getName();

        assertEquals(expectedAuthorName, actualAuthorName);
    }

    @DisplayName("удалять автора по id")
    @Test
    void shouldDeleteAuthorById() {
        Author expectedAuthor = null;
        authorDao.deleteAuthorById(3L);
        Author actualAuthor = em.find(Author.class, 3L);

        assertEquals(expectedAuthor, actualAuthor);
    }
}