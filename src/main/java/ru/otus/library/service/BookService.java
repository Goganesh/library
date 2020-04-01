package ru.otus.library.service;

import ru.otus.library.model.Author;
import ru.otus.library.model.Book;
import java.util.List;

public interface BookService {
    List<Book> getAllBooksWithAllInfo();
    List<Book> getAllBooksByAuthorWithAllInfo(Author author);
}
