package ru.otus.library.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.library.model.Book;
import ru.otus.library.model.Review;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @EntityGraph("review-book-entity-graph")
    List<Review> findByBook(@Param("book") Book book);
}
