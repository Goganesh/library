package ru.otus.library.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.library.model.Author;
import ru.otus.library.model.Book;
import ru.otus.library.model.Genre;
import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findByAuthor(Author author);

    Book findByName(String name);

    void deleteByAuthor(Author author);

    void deleteByGenres(Genre genre);
}
