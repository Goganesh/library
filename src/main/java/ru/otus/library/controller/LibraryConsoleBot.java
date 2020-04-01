package ru.otus.library.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.library.model.Author;
import ru.otus.library.model.Book;
import ru.otus.library.service.AuthorService;
import ru.otus.library.service.BookService;
import ru.otus.library.service.IOService;
import java.util.List;

@ShellComponent
@AllArgsConstructor
public class LibraryConsoleBot {

    private final BookService bookService;
    private final AuthorService authorService;
    private final IOService ioService;

    private static Logger logger = LoggerFactory.getLogger(LibraryConsoleBot.class);

    @ShellMethod(key = {"info", "i"}, value = "information about library bot")
    public void greeeting(){
        ioService.printRequest("Library Bot can help to find book you need. Database library contains artworks from all countries.");
    }

    @ShellMethod(key = {"books", "b"}, value = "Bot shows all books")
    public void showAllBooks(){
        List<Book> books = bookService.getAllBooks();
        for(Book book : books){
            ioService.printRequest("Author - " + book.getAuthor().getName() + ", book name - " + book.getName());
        }
    }

    @ShellMethod(key = {"authors", "a"}, value = "Bot shows all authors")
    public void showAllAuthors(){
        List<Author> authors = authorService.getAllAuthors();
        ioService.printRequest("Authors:");
        for(Author author : authors){
            ioService.printRequest(author.getName());
        }
    }

    @ShellMethod(key = {"authorBooks", "ab"}, value = "Bot shows all books from author")
    public void showAllBooksByAuthor(){
        String authorName = ioService.printResponse("Enter author name");
        Author author = authorService.getAuthorByName(authorName);
        List<Book> books = bookService.getAllBooksByAuthor(author);

        ioService.printRequest("Books by " + author.getName() + ":");
        for(Book book : books){
            ioService.printRequest(book.getName());
        }
    }
}
