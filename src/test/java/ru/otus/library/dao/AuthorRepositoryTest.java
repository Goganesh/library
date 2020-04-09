package ru.otus.library.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.library.model.Author;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Repository для работы с авторами")
@DataJpaTest
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository repository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("сохранять нового автора и возвращать id")
    @Test
    void shouldReturnAuthorByName() {
        Author actualAuthor = repository.findByName("Alexandre Dumas");
        Author expectedAuthor = em.find(Author.class, 1L);

        assertEquals(expectedAuthor, actualAuthor);
    }
}