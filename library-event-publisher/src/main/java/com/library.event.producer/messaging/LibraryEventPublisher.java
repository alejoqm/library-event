package com.library.event.producer.messaging;

import com.library.event.common.domain.LibraryEvent;

public interface LibraryEventPublisher {

   /**
   * Publishes the event. Returns immediately; delivery happens asynchronously.
   */
   void publish(LibraryEvent event);
}
