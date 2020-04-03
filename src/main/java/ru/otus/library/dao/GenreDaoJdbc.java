package ru.otus.library.dao;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.library.model.Book;
import ru.otus.library.model.Genre;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations jdbc;

    private static Logger logger = LoggerFactory.getLogger(GenreDaoJdbc.class);

    @Override
    public long saveGenre(Genre genre) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", genre.getName());

        jdbc.update("insert into genres (name) values (:name)", params, keyHolder);
        genre.setId(keyHolder.getKey().longValue());
        logger.info("asve genre - " + genre.toString());

        return genre.getId();
    }

    @Override
    public Genre getGenreById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        Genre genre = jdbc.queryForObject("select * from genres where id = :id", params, new GenreDaoJdbc.GenreMapper());
        logger.info("genre found - " + genre.toString());

        return genre;

    }

    @Override
    public List<Genre> getAllGenres() {
        List<Genre> genres = jdbc.query("select * from genres", new GenreDaoJdbc.GenreMapper());
        logger.info("all genres - " + genres.size());

        return genres;
    }

    @Override
    public void updateGenre(Genre genre) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", genre.getId())
                .addValue("name", genre.getName());
        jdbc.update("update genres set name = :name where id = :id", params);
        logger.info("update genre - " + genre.toString());
    }

    @Override
    public void deleteGenre(Genre genre) {
        deleteGenreById(genre.getId());
    }

    @Override
    public void deleteGenreById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbc.update(
                "delete from genres where id = :id", params
        );
        logger.info("delete genre by id - " + id);
    }

    @Override
    public List<Genre> getAllGenresByBook(Book book) {
        Map<String, Object> params = Collections.singletonMap("id", book.getId());
        List<Long> genreIds = jdbc.queryForList("select genreid from booktogenre where bookid = :id", params, Long.class);
        List<Genre> genres = new ArrayList<>();
        for(Long id : genreIds){
            genres.add(getGenreById(id));
        }
        return genres;
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Genre(id, name);
        }
    }
}
