package ru.otus.library.dao;

import ru.otus.library.model.Book;
import ru.otus.library.model.Review;
import java.util.List;

public interface ReviewDao {
    long saveReview(Review review);
    Review getReviewById(long id);
    List<Review> getReviewsByBook(Book book);
    List<Review> getAllReviews();
    void updateReview(Review review);
    void deleteReview(Review review);
    void deleteReviewById(long id);
}
