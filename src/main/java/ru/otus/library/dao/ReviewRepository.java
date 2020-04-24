package ru.otus.library.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.library.model.Book;
import ru.otus.library.model.Review;
import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, String> {

    List<Review> findByBook(Book book);
    void deleteByBook(Book book);
}
