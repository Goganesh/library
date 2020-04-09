package ru.otus.library.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.library.model.Book;
import ru.otus.library.model.Review;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByBook(Book book);
}
