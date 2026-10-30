package com.library.event.producer.ports.input;

/**
 * Inbound port for publishing book events.
 */
public interface PublishBookUseCase {

  /**
   * Publish book.
   * @param command
   */
  void publish(PublishBookCommand command);
}
