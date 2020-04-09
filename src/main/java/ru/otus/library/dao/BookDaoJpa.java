package ru.otus.library.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.model.Author;
import ru.otus.library.model.Book;

import javax.persistence.*;
import java.util.List;

@Repository
@Primary
@Transactional
public class BookDaoJpa implements BookDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public long saveBook(Book book) {
        if (book.getId() == 0) {
            em.persist(book);
        } else {
            em.merge(book);
        }
        return book.getId();
    }

    @Override
    public Book getBookByIdWithAllInfo(long id) {
        return em.find(Book.class, id);
    }

    @Override
    public Book getBookByNameWithAllInfo(String bookName) {
        EntityGraph<?> entityGraph = em.getEntityGraph("book-author-genres-entity-graph");
        TypedQuery<Book> query = em.createQuery("select s " +
                        "from Book s " +
                        "where s.name = :name",
                Book.class);
        query.setParameter("name", bookName);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getSingleResult();
    }

    @Override
    public List<Book> getAllBooksByAuthorWithAllInfo(Author author) {
        EntityGraph<?> entityGraph = em.getEntityGraph("book-author-genres-entity-graph");
        TypedQuery<Book> query = em.createQuery("select s " +
                        "from Book s " +
                        "where s.author = :author",
                Book.class);
        query.setParameter("author", author);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public List<Book> getAllBooksWithAllInfo() {
        EntityGraph<?> entityGraph = em.getEntityGraph("book-author-genres-entity-graph");
        TypedQuery<Book> query = em.createQuery("select s from Book s",
                Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public void updateBook(Book book) {
        saveBook(book);
    }

    @Override
    public void deleteBook(Book book) {
        deleteBookById(book.getId());
    }

    @Override
    public void deleteBookById(long id) {
        Query query = em.createQuery("delete from Book s where s.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
