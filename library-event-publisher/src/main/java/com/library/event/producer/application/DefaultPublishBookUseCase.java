package com.library.event.producer.application;

import com.library.event.producer.domain.BookEventType;
import com.library.event.producer.domain.BookPublishedEvent;
import com.library.event.producer.ports.input.PublishBookCommand;
import com.library.event.producer.ports.input.PublishBookUseCase;
import com.library.event.producer.ports.output.BookEventPublisher;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class DefaultPublishBookUseCase implements PublishBookUseCase {

    private final BookEventPublisher bookEventPublisher;

    public DefaultPublishBookUseCase(final BookEventPublisher bookEventPublisher) {
        this.bookEventPublisher = bookEventPublisher;
    }

    @Override
    public void publish(PublishBookCommand command) {
        var event = new BookPublishedEvent(UUID.randomUUID(), BookEventType.BOOK_PUBLISHED, Instant.now(),
                new BookPublishedEvent.BookPayload(command.title(), command.author(),
                        command.isbn()));
        bookEventPublisher.publish(event);
    }
}
