package com.library.event.producer.adapters.output;

import com.library.event.producer.domain.BookPublishedEvent;
import com.library.event.producer.ports.output.BookEventPublisher;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

@Component
public class BookEventProducerAdapter implements BookEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(BookEventProducerAdapter.class);

    private final KafkaTemplate<UUID, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public BookEventProducerAdapter(final KafkaTemplate<UUID, String> kafkaTemplate,
                                    final ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void publish(BookPublishedEvent bookPublishedEvent) {
        log.info("Publishing event {}", bookPublishedEvent.eventId());
        String payload = objectMapper.writeValueAsString(bookPublishedEvent);
        ProducerRecord<UUID, String> record = new ProducerRecord<>(
                kafkaTemplate.getDefaultTopic(),
                bookPublishedEvent.eventId(),  // ← Key is the event ID
                payload);
        kafkaTemplate.send(record).whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Failed to publish event {}", bookPublishedEvent.eventId(), ex);
            }
        });
    }
}
