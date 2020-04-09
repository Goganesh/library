package ru.otus.library.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.model.Genre;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Primary
@Transactional
public class GenreDaoJpa implements GenreDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public long saveGenre(Genre genre) {
        if (genre.getId() == 0) {
            em.persist(genre);
        } else {
            em.merge(genre).getId();
        }
        return genre.getId();
    }

    @Override
    public Genre getGenreById(long id) {
        return em.find(Genre.class, id);
    }

    @Override
    public List<Genre> getAllGenres() {
        TypedQuery<Genre> query = em.createQuery("select s from Genre s",
                Genre.class);
        return query.getResultList();
    }

    @Override
    public void updateGenre(Genre genre) {
        saveGenre(genre);
    }

    @Override
    public void deleteGenre(Genre genre) {
        deleteGenreById(genre.getId());
    }

    @Override
    public void deleteGenreById(long id) {
        Query query = em.createQuery("delete from Genre s where s.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
