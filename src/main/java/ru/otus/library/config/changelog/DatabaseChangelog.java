package ru.otus.library.config.changelog;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.otus.library.model.Author;
import ru.otus.library.model.Book;
import ru.otus.library.model.Genre;
import java.util.ArrayList;
import java.util.List;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "addAuthors", author = "Basiladze-GT")
    public void insertAuthors(/*MongoTemplate mongoTemplate*/ DB db) {
        DBCollection myCollection = db.getCollection("authors");
        BasicDBObject doc = new BasicDBObject().append("name", "Alexandre Dumas");
        myCollection.insert(doc);


        /*
        mongoTemplate.dropCollection("authors");

        List<Author> authors = new ArrayList<>();
        authors.add(new Author("Alexandre Dumas"));
        authors.add(new Author("Ernest Hemingway"));
        authors.add(new Author("Samerset Moem"));
        authors.add(new Author("Alexander Pushkin"));
        authors.add(new Author("Barbara Sher"));
        authors.forEach(author -> mongoTemplate.save(author));*/
    }

    /*@ChangeSet(order = "002", id = "addGenres", author = "Basiladze-GT")
    public void insertGenres(MongoTemplate mongoTemplate) {
        mongoTemplate.dropCollection("genres");

        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre("Drama"));
        genres.add(new Genre("Novel"));
        genres.add(new Genre("Comedy"));
        genres.add(new Genre("Thriller"));
        genres.add(new Genre("Psychology"));
        genres.add(new Genre("Poetry"));
        genres.forEach(genre -> mongoTemplate.save(genre));
    }

    @ChangeSet(order = "003", id = "addBooks", author = "Basiladze-GT")
    public void insertBooks(MongoTemplate mongoTemplate) {
        mongoTemplate.dropCollection(Book.class);

        Book book1 = new Book();
        book1.setName("The Three Musketeers");
        Query authorQuery = new Query();
        authorQuery.addCriteria(Criteria.where("name").is("Alexandre Dumas"));
        List<Author> authorList = mongoTemplate.find(authorQuery, Author.class, "authors");
        book1.setAuthor(authorList.get(0));
        //Query genreQuery = new Query();
        //genreQuery.addCriteria(Criteria.where("name").is("Alexandre Dumas"));
        //List<Genre> genresList = mongoTemplate.find(genreQuery, Genre.class, "genres");
        //book1.setGenres(genresList);
        mongoTemplate.save(book1);

    }*/
}
