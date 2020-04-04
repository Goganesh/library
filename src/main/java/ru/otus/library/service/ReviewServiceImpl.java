package ru.otus.library.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.dao.ReviewDao;
import ru.otus.library.model.Book;
import ru.otus.library.model.Review;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewDao reviewDao;

    @Override
    public List<Review> getReviewsByBook(Book book) {
        return reviewDao.getReviewsByBook(book);
    }

    @Override
    public long saveReview(Review review) {
        return reviewDao.saveReview(review);
    }
}
