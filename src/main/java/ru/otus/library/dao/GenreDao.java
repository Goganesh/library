package ru.otus.library.dao;

import ru.otus.library.model.Genre;
import java.util.List;

public interface GenreDao {
    long saveGenre(Genre genre);
    Genre getGenreById(long id);
    List<Genre> getAllGenres();
    void updateGenre(Genre genre);
    void deleteGenre(Genre genre);
    void deleteGenreById(long id);
}
