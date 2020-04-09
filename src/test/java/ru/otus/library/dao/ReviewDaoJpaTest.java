package ru.otus.library.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.library.model.Book;
import ru.otus.library.model.Genre;
import ru.otus.library.model.Review;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Dao JPA для работы с отзывами")
@DataJpaTest
@Import({ReviewDaoJpa.class, BookDaoJpa.class})
class ReviewDaoJpaTest {

    @Autowired
    private ReviewDao reviewDao;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private TestEntityManager em;

    @DisplayName("сохранять новый отзыв и возвращать id")
    @Test
    void shouldSaveNewReviewAndReturnId() {
        Book book = bookDao.getBookByIdWithAllInfo(3L);
        Review expectedReview = new Review("Interesting story about the confrontation between nature and man", book);
        long expectedId = reviewDao.saveReview(expectedReview);
        Review actualReview = em.find(Review.class, expectedId);

        assertEquals(expectedReview, actualReview);
    }

    @DisplayName("возвращать отзыв по id")
    @Test
    void shouldReturnReviewById() {
        Review actualReview = reviewDao.getReviewById(1L);
        Review expectedReview = em.find(Review.class, 1L);

        assertEquals(expectedReview, actualReview);
    }

    @DisplayName("возвращать отзыв по книге")
    @Test
    void shouldReturnReviewByBook() {
        Book expectedBook = em.find(Book.class, 1L);
        Review expectedReview1 = em.find(Review.class, 1L);
        Review expectedReview2 = em.find(Review.class, 2L);
        List<Review> expectedReviews = new ArrayList<>();
        expectedReviews.add(expectedReview1);
        expectedReviews.add(expectedReview2);

        List<Review> actualReviews = reviewDao.getReviewsByBook(expectedBook);

        assertEquals(expectedReviews, actualReviews);
    }

    @DisplayName("возвращать все отзывы")
    @Test
    void shouldReturnAllReviews() {
        int expectedSize = 3;
        int actualSize = reviewDao.getAllReviews().size();

        assertEquals(expectedSize, actualSize);
    }

    @DisplayName("обновлять отзывы")
    @Test
    void shouldUpdateGenreName() {
        String expectedReview = "NEW REVIEW";
        Review actualReview = reviewDao.getReviewById(1L);
        actualReview.setReview(expectedReview);
        reviewDao.updateReview(actualReview);
        String actualReviewName = em.find(Review.class, 1L).getReview();

        assertEquals(expectedReview, actualReviewName);
    }

    @DisplayName("удалять отзыв по id")
    @Test
    void shouldDeleteReviewById() {
        Review expectedReview = null;
        reviewDao.deleteReviewById(1L);
        Review actualReview = em.find(Review.class, 1L);

        assertEquals(expectedReview, actualReview);
    }
}