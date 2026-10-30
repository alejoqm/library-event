package com.library.event.producer.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.library.event.producer.domain.BookEventType;
import com.library.event.producer.domain.BookPublishedEvent;
import com.library.event.producer.ports.input.PublishBookCommand;
import com.library.event.producer.ports.output.BookEventPublisher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DefaultPublishBookUseCaseTest {
  @Mock private BookEventPublisher bookEventPublisher;

  @InjectMocks private DefaultPublishBookUseCase useCase;

  @Test
  void shouldTransformCommandToEventAndPublish() {
    // given
    var command = new PublishBookCommand("Effective Java", "Joshua Bloch", "9780134685991");

    // when
    useCase.publish(command);

    // then
    var captor = ArgumentCaptor.forClass(BookPublishedEvent.class);
    verify(bookEventPublisher, times(1)).publish(captor.capture());

    var event = captor.getValue();
    assertThat(event.eventId()).isNotNull();
    assertThat(event.eventType()).isEqualTo(BookEventType.BOOK_PUBLISHED);
    assertThat(event.occurredAt()).isNotNull();
    assertThat(event.book().title()).isEqualTo("Effective Java");
    assertThat(event.book().author()).isEqualTo("Joshua Bloch");
    assertThat(event.book().isbn()).isEqualTo("9780134685991");
  }
}
