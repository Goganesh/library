package ru.otus.library.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.library.model.Book;
import ru.otus.library.model.Review;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Repository для работы с отзывами")
@DataJpaTest
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository repository;
    @Autowired
    private TestEntityManager em;

    @DisplayName("возвращать отзыв по книге")
    @Test
    void shouldReturnReviewByBook() {
        Book expectedBook = em.find(Book.class, 1L);
        List<Review> expectedReviews = new ArrayList<>();
        expectedReviews.add(em.find(Review.class, 1L));
        expectedReviews.add(em.find(Review.class, 2L));

        List<Review> actualReviews = repository.findByBook(expectedBook);

        assertEquals(expectedReviews, actualReviews);
    }
}