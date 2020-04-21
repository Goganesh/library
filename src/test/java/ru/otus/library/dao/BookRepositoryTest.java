package ru.otus.library.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.library.AbstractRepositoryTest;
import ru.otus.library.model.Author;
import ru.otus.library.model.Book;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Repository для работы с авторами")
class BookRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @DisplayName("удалять книги по автору")
    @Test
    void shouldDeleteBooksByAuthor() {
        Author authorForDelete = authorRepository.findByName("Alexandre Dumas");
        bookRepository.deleteByAuthor(authorForDelete);
        List<Book> actualBooks = bookRepository.findByAuthor(authorForDelete);
        int expectedSize = 0;

        assertEquals(expectedSize, actualBooks.size());
    }
}