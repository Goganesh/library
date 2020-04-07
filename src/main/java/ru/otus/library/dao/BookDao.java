package ru.otus.library.dao;

import ru.otus.library.model.Author;
import ru.otus.library.model.Book;
import ru.otus.library.model.Genre;

import java.util.List;

public interface BookDao {
    long saveBook(Book book);
    Book getBookByIdWithAllInfo(long id);
    List<Book> getAllBooksByAuthorWithAllInfo(Author author);
    List<Book> getAllBooksWithAllInfo();
    void updateBook(Book book);
    void deleteBook(Book book);
    void deleteBookById(long id);
}
