package ru.otus.library.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.library.model.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {
    Author findByName(String name);
}
