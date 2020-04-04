package ru.otus.library.dao;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.model.Book;
import ru.otus.library.model.Genre;
import ru.otus.library.model.Review;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
@AllArgsConstructor
public class ReviewDaoJpa implements ReviewDao {

    @PersistenceContext
    private EntityManager em;

    private static Logger logger = LoggerFactory.getLogger(ReviewDaoJpa.class);

    @Override
    public long saveReview(Review review) {
        if (review.getId() == 0) {
            Session session = em.unwrap(Session.class);
            Long id = (Long) session.save(review);
            review.setId(id);
        } else {
            em.merge(review).getId();
        }
        logger.info("save review - " + review.toString());
        return review.getId();
    }

    @Override
    public List<Review> getReviewsByBook(Book book) {
        TypedQuery<Review> query = em.createQuery("select s from Review s where s.book = :book",
                Review.class);
        query.setParameter("book", book);
        return query.getResultList();
    }

    @Override
    public Review getReviewById(long id) {
        Review review = em.find(Review.class, id);
        logger.info("review found - " + review.toString());
        return review;
    }

    @Override
    public List<Review> getAllReviews() {
        TypedQuery<Review> query = em.createQuery("select s from Review s",
                Review.class);
        return query.getResultList();
    }

    @Override
    public void updateReview(Review review) {
        saveReview(review);
    }

    @Override
    public void deleteReview(Review review) {
        deleteReviewById(review.getId());
    }

    @Override
    public void deleteReviewById(long id) {
        Query query = em.createQuery("delete from Review s where s.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
