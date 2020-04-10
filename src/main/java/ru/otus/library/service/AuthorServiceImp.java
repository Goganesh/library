package ru.otus.library.service;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.otus.library.dao.AuthorRepository;
import ru.otus.library.model.Author;
import java.util.List;

@Service
@Primary
@AllArgsConstructor
public class AuthorServiceImp implements AuthorService {

    private final AuthorRepository repository;

    @Override
    public Author getAuthorByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<Author> getAllAuthors() {
        return repository.findAll();
    }
}
