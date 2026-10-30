package com.library.event.producer.adapters.output;

import com.library.event.producer.domain.BookPublishedEvent;
import com.library.event.producer.ports.output.BookEventPublisher;
import java.util.UUID;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class BookEventProducerAdapter implements BookEventPublisher {

  /** Logger for this class. */
  private static final Logger LOG = LoggerFactory.getLogger(BookEventProducerAdapter.class);

  /** KafkaTemplate to publish Event. */
  private final KafkaTemplate<UUID, String> kafkaTemplate;

  /** Mapper to convert object before to publish event. */
  private final ObjectMapper objectMapper;

  /**
   * Setting kafka template to send messages and object mapper.
   * @param kafkaTemplate
   * @param objectMapper
   */
  public BookEventProducerAdapter(
      final KafkaTemplate<UUID, String> kafkaTemplate, final ObjectMapper objectMapper) {
    this.kafkaTemplate = kafkaTemplate;
    this.objectMapper = objectMapper;
  }

  /**
   * Publishing event in Kafka.
   * @param bookPublishedEvent
   */
  @Override
  public void publish(BookPublishedEvent bookPublishedEvent) {
    LOG.info("Publishing event {}", bookPublishedEvent.eventId());
    String payload = objectMapper.writeValueAsString(bookPublishedEvent);
    ProducerRecord<UUID, String> record =
        new ProducerRecord<>(
            kafkaTemplate.getDefaultTopic(),
            bookPublishedEvent.eventId(), // ← Key is the event ID
            payload);
    kafkaTemplate
        .send(record)
        .whenComplete(
            (result, ex) -> {
              if (ex != null) {
                LOG.error("Failed to publish event {}", bookPublishedEvent.eventId(), ex);
              }
            });
  }
}
