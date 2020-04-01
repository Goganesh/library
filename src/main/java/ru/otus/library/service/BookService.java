package ru.otus.library.service;

import ru.otus.library.model.Author;
import ru.otus.library.model.Book;
import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    List<Book> getAllBooksByAuthor(Author author);
}
