package ru.otus.library.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.model.Author;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Primary
@Transactional
public class AuthorDaoJpa implements AuthorDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public long saveAuthor(Author author) {
        if (author.getId() == 0) {
            em.persist(author);
        } else {
            em.merge(author);
        }
        return author.getId();
    }

    @Override
    public Author getAuthorById(long id) {
        return em.find(Author.class, id);
    }

    @Override
    public Author getAuthorByName(String name) {
        TypedQuery<Author> query = em.createQuery("select s " +
                        "from Author s " +
                        "where s.name = :name",
                Author.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public List<Author> getAllAuthors() {
        TypedQuery<Author> query = em.createQuery("select s from Author s",
                Author.class);
        return query.getResultList();
    }

    @Override
    public void updateAuthor(Author author) {
        saveAuthor(author);
    }

    @Override
    public void deleteAuthor(Author author) {
        deleteAuthorById(author.getId());
    }

    @Override
    public void deleteAuthorById(long id) {
        Query query = em.createQuery("delete from Author s where s.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
