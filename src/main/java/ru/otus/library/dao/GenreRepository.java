package ru.otus.library.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.library.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
