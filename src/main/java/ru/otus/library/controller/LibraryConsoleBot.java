package ru.otus.library.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.library.model.Author;
import ru.otus.library.model.Book;
import ru.otus.library.model.Review;
import ru.otus.library.service.AuthorService;
import ru.otus.library.service.BookService;
import ru.otus.library.service.IOService;
import ru.otus.library.service.ReviewService;
import java.util.List;

@ShellComponent
@AllArgsConstructor
public class LibraryConsoleBot {

    private final BookService bookService;
    private final AuthorService authorService;
    private final IOService ioService;
    private final ReviewService reviewService;

    private static Logger logger = LoggerFactory.getLogger(LibraryConsoleBot.class);

    @ShellMethod(key = {"info", "i"}, value = "Information about library bot")
    public void greeeting(){
        ioService.printRequest("Library Bot can help to find book you need. Database library contains artworks from all countries.");
    }

    @ShellMethod(key = {"books", "b"}, value = "Bot shows all books")
    public void showAllBooks(){
        List<Book> books = bookService.getAllBooksWithAllInfo();
        books.forEach(book -> ioService.printRequest("Author - " + book.getAuthor().getName() + ", book name - " + book.getName()));
    }

    @ShellMethod(key = {"authors", "a"}, value = "Bot shows all authors")
    public void showAllAuthors(){
        List<Author> authors = authorService.getAllAuthors();
        ioService.printRequest("Authors:");
        authors.forEach(author -> ioService.printRequest(author.getName()));
    }

    @ShellMethod(key = {"authorBooks", "ab"}, value = "Bot shows all books from author")
    public void showAllBooksByAuthor(){
        String authorName = ioService.printResponse("Enter author name");
        Author author = authorService.getAuthorByName(authorName);
        List<Book> books = bookService.getAllBooksByAuthorWithAllInfo(author);

        ioService.printRequest("Books by " + author.getName() + ":");
        books.forEach(book -> ioService.printRequest(book.getName()));
    }

    @ShellMethod(key = {"reviewsBook", "rb"}, value = "Bot shows all reviews by book")
    public void showAllReviewsByBook(){
        String bookName = ioService.printResponse("Enter book name");
        Book book = bookService.getBookByNameWithAllInfo(bookName);
        List<Review> reviews = reviewService.getReviewsByBook(book);
        reviews.forEach(review -> ioService.printRequest(review.getReview()));
    }

    @ShellMethod(key = {"createReview", "cr"}, value = "Bot save your review by book")
    public void createReviewByBook(){
        String bookName = ioService.printResponse("Enter book name");
        Book book = bookService.getBookByNameWithAllInfo(bookName);
        String bookReview = ioService.printResponse("Enter review by book - " + book.getName());
        Review review = new Review();
        review.setReview(bookReview);
        reviewService.saveReview(review);
        review.setBook(book);
        reviewService.saveReview(review);
        ioService.printRequest("review saved");
    }
}
