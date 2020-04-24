package ru.otus.library.service;

import ru.otus.library.model.Book;
import ru.otus.library.model.Review;
import java.util.List;

public interface ReviewService {
    List<Review> getReviewsByBook(Book book);
    String saveReview(Review review);
}
