package ru.otus.library.events;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.library.AbstractRepositoryTest;
import ru.otus.library.dao.AuthorRepository;
import ru.otus.library.dao.BookRepository;
import ru.otus.library.dao.ReviewRepository;
import ru.otus.library.model.Author;
import ru.otus.library.model.Book;
import ru.otus.library.model.Review;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("EventListener для каскадного удаления отзывов по книге")
@ComponentScan("ru.otus.library.events")
class MongoBookCascadeDeleteEventsListenerTest extends AbstractRepositoryTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    @DisplayName("после удаления книги должны удалиться все отзывы по этой книге")
    void afterAuthorDeleteShouldDeleteAllHisBook() {
        Book bookForDelete = bookRepository.findByName("The Three Musketeers");

        bookRepository.delete(bookForDelete);
        List<Review> actualReviews = reviewRepository.findByBook(bookForDelete);
        int expectedSize = 0;
        assertEquals(expectedSize, actualReviews.size());
    }
}