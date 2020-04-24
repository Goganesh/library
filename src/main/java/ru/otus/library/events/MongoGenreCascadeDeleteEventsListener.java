package ru.otus.library.events;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.bson.Document;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.library.dao.BookRepository;
import ru.otus.library.dao.GenreRepository;
import ru.otus.library.exception.MongoCascadeDeleteEventsListenerException;
import ru.otus.library.model.Genre;

@Component
@AllArgsConstructor
public class MongoGenreCascadeDeleteEventsListener extends AbstractMongoEventListener<Genre> {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;

    @SneakyThrows
    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Genre> event) {
        super.onBeforeDelete(event);
        Document source =  event.getSource();
        String genreId = source.get("_id").toString();
        Genre deletedGenre = genreRepository.findById(genreId).orElseThrow(() ->
                new MongoCascadeDeleteEventsListenerException("where is no deleted object"));
        bookRepository.deleteByGenres(deletedGenre);
    }
}
