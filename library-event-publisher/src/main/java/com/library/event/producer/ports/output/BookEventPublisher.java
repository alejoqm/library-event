package com.library.event.producer.ports.output;

import com.library.event.producer.domain.BookPublishedEvent;
import com.library.event.producer.ports.input.PublishBookCommand;

public interface BookEventPublisher {

    /**
     * Publishes the event. Returns immediately; delivery happens asynchronously.
     */
    void publish(BookPublishedEvent bookPublishedEvent);

}
