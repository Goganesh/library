package ru.otus.library.events;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.bson.Document;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.library.dao.AuthorRepository;
import ru.otus.library.dao.BookRepository;
import ru.otus.library.exception.MongoCascadeDeleteEventsListenerException;
import ru.otus.library.model.Author;

@Component
@AllArgsConstructor
public class MongoAuthorCascadeDeleteEventsListener extends AbstractMongoEventListener<Author> {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @SneakyThrows
    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Author> event) {
        super.onBeforeDelete(event);
        Document source =  event.getSource();
        String authorId = source.get("_id").toString();
        Author deletedAuthor = authorRepository.findById(authorId).orElseThrow(() ->
                new MongoCascadeDeleteEventsListenerException("where is no deleted object"));
        bookRepository.deleteByAuthor(deletedAuthor);
    }
}
