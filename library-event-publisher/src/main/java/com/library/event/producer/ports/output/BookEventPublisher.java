package com.library.event.producer.ports.output;

import com.library.event.producer.domain.BookPublishedEvent;

public interface BookEventPublisher {

  /** Publishes the event. Returns immediately; delivery happens asynchronously. */
  void publish(BookPublishedEvent bookPublishedEvent);
}
