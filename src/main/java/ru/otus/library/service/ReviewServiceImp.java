package ru.otus.library.service;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.otus.library.dao.ReviewRepository;
import ru.otus.library.model.Book;
import ru.otus.library.model.Review;

import java.util.List;

@Service
@Primary
@AllArgsConstructor
public class ReviewServiceImp implements ReviewService {

    private final ReviewRepository repository;

    @Override
    public List<Review> getReviewsByBook(Book book) {
        return repository.findByBook(book);
    }

    @Override
    public long saveReview(Review review) {
        return repository.saveAndFlush(review).getId();
    }
}
