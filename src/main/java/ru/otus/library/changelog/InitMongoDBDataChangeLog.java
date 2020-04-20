package ru.otus.library.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.library.model.Author;
import ru.otus.library.model.Book;
import ru.otus.library.model.Genre;
import ru.otus.library.model.Review;
import java.util.ArrayList;
import java.util.List;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    @ChangeSet(order = "000", id = "dropDB", author = "Basiladze-GT", runAlways = true)
    public void dropDB(MongoDatabase database){
        database.drop();
    }

    @ChangeSet(order = "001", id = "addAuthors", author = "Basiladze-GT", runAlways = true)
    public void insertAuthors(MongoTemplate template){
        List<Author> authors = new ArrayList<>();
        authors.add(new Author("Alexandre Dumas"));
        authors.add(new Author("Ernest Hemingway"));
        authors.add(new Author("Samerset Moem"));
        authors.add(new Author("Alexander Pushkin"));
        authors.add(new Author("Barbara Sher"));

        authors.forEach(author -> template.save(author));
    }

    @ChangeSet(order = "002", id = "addGenres", author = "Basiladze-GT", runAlways = true)
    public void insertGenres(MongoTemplate template) {
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre("Drama"));
        genres.add(new Genre("Novel"));
        genres.add(new Genre("Comedy"));
        genres.add(new Genre("Thriller"));
        genres.add(new Genre("Psychology"));
        genres.add(new Genre("Poetry"));

        genres.forEach(genre -> template.save(genre));
    }

    @ChangeSet(order = "003", id = "addBooks", author = "Basiladze-GT", runAlways = true)
    public void insertBooks(MongoTemplate template) {
        List<Book> books = new ArrayList<>();
        Query authorQuery;
        Query genreQuery;
        List<String> genresName;


        Book book1 = new Book();
        book1.setName("The Three Musketeers");
        authorQuery = new Query();
        authorQuery.addCriteria(Criteria.where("name").is("Alexandre Dumas"));
        book1.setAuthor(template.find(authorQuery, Author.class).get(0));
        genreQuery = new Query();
        genresName = new ArrayList<>();
        genresName.add("Novel");
        genresName.add("Comedy");
        genreQuery.addCriteria(Criteria.where("name").in(genresName));
        book1.setGenres(template.find(genreQuery, Genre.class));
        books.add(book1);

        Book book2 = new Book();
        book2.setName("The Count of Monte Cristo");
        authorQuery = new Query();
        authorQuery.addCriteria(Criteria.where("name").is("Alexandre Dumas"));
        book2.setAuthor(template.find(authorQuery, Author.class).get(0));
        genreQuery = new Query();
        genresName = new ArrayList<>();
        genresName.add("Novel");
        genresName.add("Drama");
        genreQuery.addCriteria(Criteria.where("name").in(genresName));
        book2.setGenres(template.find(genreQuery, Genre.class));
        books.add(book2);

        Book book3 = new Book();
        book3.setName("The Old Man and the Sea");
        authorQuery = new Query();
        authorQuery.addCriteria(Criteria.where("name").is("Ernest Hemingway"));
        book3.setAuthor(template.find(authorQuery, Author.class).get(0));
        genreQuery = new Query();
        genresName = new ArrayList<>();
        genresName.add("Drama");
        genreQuery.addCriteria(Criteria.where("name").in(genresName));
        book3.setGenres(template.find(genreQuery, Genre.class));
        books.add(book3);

        Book book4 = new Book();
        book4.setName("Wishcraft");
        authorQuery = new Query();
        authorQuery.addCriteria(Criteria.where("name").is("Barbara Sher"));
        book4.setAuthor(template.find(authorQuery, Author.class).get(0));
        genreQuery = new Query();
        genresName = new ArrayList<>();
        genresName.add("Psychology");
        genreQuery.addCriteria(Criteria.where("name").in(genresName));
        book4.setGenres(template.find(genreQuery, Genre.class));
        books.add(book4);

        Book book5 = new Book();
        book5.setName("Ruslan and Ludmila");
        authorQuery = new Query();
        authorQuery.addCriteria(Criteria.where("name").is("Alexander Pushkin"));
        book5.setAuthor(template.find(authorQuery, Author.class).get(0));
        genreQuery = new Query();
        genresName = new ArrayList<>();
        genresName.add("Poetry");
        genreQuery.addCriteria(Criteria.where("name").in(genresName));
        book5.setGenres(template.find(genreQuery, Genre.class));
        books.add(book5);

        books.forEach(book -> template.save(book));
    }

    @ChangeSet(order = "004", id = "addReviews", author = "Basiladze-GT", runAlways = true)
    public void insertReviews(MongoTemplate template) {
        List<Review> reviews = new ArrayList<>();
        Query bookQuery;

        Review review1 = new Review();
        review1.setReview("A great novel, imbued with the spirit of unclean maximalism");
        bookQuery = new Query();
        bookQuery.addCriteria(Criteria.where("name").is("The Three Musketeers"));
        review1.setBook(template.find(bookQuery, Book.class).get(0));
        reviews.add(review1);

        Review review2 = new Review();
        review2.setReview("Good novel, read the whole trilogy");
        bookQuery = new Query();
        bookQuery.addCriteria(Criteria.where("name").is("The Three Musketeers"));
        review2.setBook(template.find(bookQuery, Book.class).get(0));
        reviews.add(review2);

        Review review3 = new Review();
        review3.setReview("I recommend to everyone");
        bookQuery = new Query();
        bookQuery.addCriteria(Criteria.where("name").is("The Count of Monte Cristo"));
        review3.setBook(template.find(bookQuery, Book.class).get(0));
        reviews.add(review3);

        reviews.forEach(review -> template.save(review));
    }
}
