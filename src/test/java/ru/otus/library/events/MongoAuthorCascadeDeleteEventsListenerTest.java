package ru.otus.library.events;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.library.AbstractRepositoryTest;
import ru.otus.library.dao.AuthorRepository;
import ru.otus.library.dao.BookRepository;
import ru.otus.library.model.Author;
import ru.otus.library.model.Book;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("EventListener для каскадного удаления книг по автору")
@ComponentScan("ru.otus.library.events")
class MongoAuthorCascadeDeleteEventsListenerTest extends AbstractRepositoryTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    @DisplayName("после удаления автора должен удалить все его книги")
    void afterAuthorDeleteShouldDeleteAllHisBook() {
        Author authorForDelete = authorRepository.findByName("Alexandre Dumas");
        authorRepository.delete(authorForDelete);
        List<Book> actualBooks = bookRepository.findByAuthor(authorForDelete);
        int expectedSize = 0;
        assertEquals(expectedSize, actualBooks.size());
    }
}