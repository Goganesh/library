package ru.otus.library.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.dao.AuthorDao;
import ru.otus.library.model.Author;
import java.util.List;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    @Override
    public Author getAuthorByName(String name) {
        return authorDao.getAuthorByName(name);
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorDao.getAllAuthors();
    }
}
