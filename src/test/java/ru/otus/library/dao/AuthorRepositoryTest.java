package ru.otus.library.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.library.AbstractRepositoryTest;
import ru.otus.library.model.Author;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Repository для работы с авторами")
class AuthorRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private AuthorRepository repository;

    @DisplayName("возвращать автора по имени")
    @Test
    void shouldReturnAuthorByName() {
        String expectedName = "Alexandre Dumas";
        Author actualAuthor = repository.findByName(expectedName);

        assertEquals(expectedName, actualAuthor.getName());
    }
}