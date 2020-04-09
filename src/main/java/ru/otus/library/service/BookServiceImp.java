package ru.otus.library.service;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.otus.library.dao.BookRepository;
import ru.otus.library.model.Author;
import ru.otus.library.model.Book;

import java.util.List;

@Service
@Primary
@AllArgsConstructor
public class BookServiceImp implements BookService {

    private final BookRepository repository;

    @Override
    public List<Book> getAllBooksWithAllInfo() {
        return repository.findAll();
    }

    @Override
    public List<Book> getAllBooksByAuthorWithAllInfo(Author author) {
        return repository.findByAuthor(author);
    }

    @Override
    public Book getBookByNameWithAllInfo(String bookName) {
        return repository.findByName(bookName);
    }
}
