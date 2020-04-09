package ru.otus.library.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.library.model.Genre;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Dao JPA для работы с жанрами")
@DataJpaTest
@Import(GenreDaoJpa.class)
class GenreDaoJpaTest {

    @Autowired
    private GenreDao genreDao;

    @Autowired
    private TestEntityManager em;

    @DisplayName("сохранять новый жанр и возвращать id")
    @Test
    void shouldSaveNewGenreAndReturnId() {
        Genre expectedGenre = new Genre("Horror");
        long expectedId = genreDao.saveGenre(expectedGenre);
        expectedGenre.setId(expectedId);
        Genre actualGenre = em.find(Genre.class, expectedId);

        assertEquals(expectedGenre, actualGenre);
    }

    @DisplayName("возвращать жанр по id")
    @Test
    void shouldReturnGenreById() {
        Genre actualGenre = genreDao.getGenreById(1L);
        Genre expectedGenre = em.find(Genre.class, 1L);
        assertEquals(expectedGenre, actualGenre);
    }

    @DisplayName("обновлять имя жанра")
    @Test
    void shouldUpdateGenreName() {
        String expectedGenreName = "NEW GENRE";
        Genre actualGenre = new Genre(1L, expectedGenreName);
        genreDao.updateGenre(actualGenre);
        String actualGenreName = em.find(Genre.class, 1L).getName();

        assertEquals(expectedGenreName, actualGenreName);
    }

    @DisplayName("удалять жанр по id")
    @Test
    void shouldDeleteGenreById() {
        Genre expectedGenre = null;
        genreDao.deleteGenreById(4L);
        Genre actualGenre = em.find(Genre.class, 4L);

        assertEquals(expectedGenre, actualGenre);
    }

}