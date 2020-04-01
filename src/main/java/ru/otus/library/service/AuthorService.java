package ru.otus.library.service;

import ru.otus.library.model.Author;

import java.util.List;

public interface AuthorService {
    Author getAuthorByName(String name);
    List<Author> getAllAuthors();
}
