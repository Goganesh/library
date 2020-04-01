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
import ru.otus.library.model.Author;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository("authorDao")
@AllArgsConstructor
public class AuthorDaoJdbc implements  AuthorDao{

    private final NamedParameterJdbcOperations jdbc;

    private static Logger logger = LoggerFactory.getLogger(AuthorDaoJdbc.class);

    @Override
    public long saveAuthor(Author author) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", author.getName());

        jdbc.update("insert into authors (name) values (:name)", params, keyHolder);
        author.setId(keyHolder.getKey().longValue());
        logger.info("save author - " + author.toString());

        return author.getId();
    }

    @Override
    public Author getAuthorById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        Author author = jdbc.queryForObject("select * from authors where id = :id", params, new AuthorMapper());
        logger.info("author found - " + author.toString());

        return author;
    }

    @Override
    public Author getAuthorByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        Author author = jdbc.queryForObject("select * from authors where name = :name", params, new AuthorMapper());
        logger.info("author found - " + author.toString());

        return author;
    }

    @Override
    public List<Author> getAllAuthors() {
        List<Author> authors = jdbc.query("select * from authors", new AuthorMapper());
        logger.info("all authors - " + authors.size());

        return authors;
    }

    @Override
    public void updateAuthor(Author author) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", author.getId())
                .addValue("name", author.getName());
        jdbc.update("update authors set name = :name where id = :id", params);
        logger.info("update author - " + author.toString());
    }

    @Override
    public void deleteAuthor(Author author) {
        deleteAuthorById(author.getId());
    }

    @Override
    public void deleteAuthorById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbc.update("delete from authors where id = :id", params);
        logger.info("delete author by id - " + id);
    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Author(id, name);
        }
    }
}
