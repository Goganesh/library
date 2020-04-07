package ru.otus.library.dao;

import ru.otus.library.model.Author;

import java.util.List;

public interface AuthorDao {
    long saveAuthor(Author author);
    Author getAuthorById(long id);
    Author getAuthorByName(String name);
    List<Author> getAllAuthors();
    void updateAuthor(Author author);
    void deleteAuthor(Author author);
    void deleteAuthorById(long id);
}
