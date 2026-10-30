package com.library.event.producer.application;

import com.library.event.producer.domain.BookEventType;
import com.library.event.producer.domain.BookPublishedEvent;
import com.library.event.producer.ports.input.PublishBookCommand;
import com.library.event.producer.ports.input.PublishBookUseCase;
import com.library.event.producer.ports.output.BookEventPublisher;
import java.time.Instant;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class DefaultPublishBookUseCase implements PublishBookUseCase {

  /** Outbound port for publishing book events to the messaging infrastructure. */
  private final BookEventPublisher bookEventPublisher;

  /**
   * Injecting publisher port.
   * @param bookEventPublisher
   */
  public DefaultPublishBookUseCase(final BookEventPublisher bookEventPublisher) {
    this.bookEventPublisher = bookEventPublisher;
  }


  /**
   * Using adapter to publish message.
   * @param command
   */
  @Override
  public void publish(PublishBookCommand command) {
    var event =
        new BookPublishedEvent(
            UUID.randomUUID(),
            BookEventType.BOOK_PUBLISHED,
            Instant.now(),
            new BookPublishedEvent.BookPayload(command.title(), command.author(), command.isbn()));
    bookEventPublisher.publish(event);
  }
}
