package ru.otus.library.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.library.model.Genre;

public interface GenreRepository extends MongoRepository<Genre, String> {

}
