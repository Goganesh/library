package ru.otus.library.dao;

import ru.otus.library.model.Author;
import ru.otus.library.model.Book;
import ru.otus.library.model.Genre;

import java.util.List;

public interface BookDao {
    long saveBook(Book book);
    Book getBookById(long id);
    List<Book> getAllBooksByAuthor(Author author);
    List<Book> getAllBooks();
    void updateBook(Book book);
    void deleteBook(Book book);
    void deleteBookById(long id);
}
