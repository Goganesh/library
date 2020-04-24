package ru.otus.library.events;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.bson.Document;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.library.dao.BookRepository;
import ru.otus.library.dao.ReviewRepository;
import ru.otus.library.exception.MongoCascadeDeleteEventsListenerException;
import ru.otus.library.model.Book;

@Component
@AllArgsConstructor
public class MongoBookCascadeDeleteEventsListener extends AbstractMongoEventListener<Book> {

    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;

    @SneakyThrows
    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Book> event) {
        super.onBeforeDelete(event);
        Document source =  event.getSource();
        String bookId = source.get("_id").toString();
        Book deletedBook = bookRepository.findById(bookId).orElseThrow(() ->
                new MongoCascadeDeleteEventsListenerException("where is no deleted object"));
        reviewRepository.deleteByBook(deletedBook);
    }
}
