package com.library.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.library.event.common.domain.Book;
import com.library.event.common.domain.LibraryEvent;
import com.library.event.producer.messaging.LibraryEventPublisher;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class LibraryEventPublisherTest {

  public static final String LIBRARY_EVENTS = "library-events";
  public static final int INT = 1;
  @Spy
  private ObjectMapper objectMapper;

  @InjectMocks
  private LibraryEventPublisher libraryEventProducer;

  @Mock
  private KafkaTemplate<Integer, String> kafkaTemplate;

  @Test
  public void testLibraryEventWithListenerFuture_failure() {
    LibraryEvent libraryEvent = LibraryEvent.builder()
        .id(null)
        .book(Book.builder().name("Name").author("Author").build())
        .build();

  }

  @Test
  public void testPostLibraryEvent_success() throws Exception {
    LibraryEvent libraryEvent = LibraryEvent.builder()
        .id(UUID.randomUUID())
        .book(Book.builder().name("Name").author("Author").build())
        .build();
  }
}
