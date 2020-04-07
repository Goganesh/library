package ru.otus.library.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.dao.BookDao;
import ru.otus.library.model.Author;
import ru.otus.library.model.Book;

import java.util.List;

@Service("bookService")
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    @Override
    public List<Book> getAllBooksWithAllInfo() {
        return bookDao.getAllBooksWithAllInfo();
    }

    @Override
    public List<Book> getAllBooksByAuthorWithAllInfo(Author author) {
        return bookDao.getAllBooksByAuthorWithAllInfo(author);
    }
}
