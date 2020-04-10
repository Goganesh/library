package ru.otus.library.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.library.model.Author;
import ru.otus.library.model.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthor(Author author);
    Book findByName(String name);

    @Override
    @Query("select distinct b from Book b join fetch b.author join fetch b.genres")
    List<Book> findAll();
}
