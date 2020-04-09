package ru.otus.library.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.library.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findByName(String name);
}
