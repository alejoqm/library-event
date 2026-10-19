package com.library.event.producer.messaging.kafka;

import com.library.event.common.domain.LibraryEvent;
import com.library.event.producer.messaging.LibraryEventPublisher;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaLibraryEventPublisher implements LibraryEventPublisher {

    private static final String EVENT_SOURCE_HEADER = "event-source";

    private final KafkaTemplate<UUID, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void publish(LibraryEvent event) {

        if (kafkaTemplate.getDefaultTopic() == null) {
            throw new IllegalArgumentException("Topic is null");
        }

        String payload = objectMapper.writeValueAsString(event);

        ProducerRecord<UUID, String> record = new ProducerRecord<>(
                kafkaTemplate.getDefaultTopic(),
                null,
                event.getId(),
                payload,
                headers());

        kafkaTemplate.send(record)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to publish event {}", event.getId(), ex);
                    } else {
                        log.info("Published event {} to partition {}",
                                event.getId(), result.getRecordMetadata().partition());
                    }
                });
    }

    private List<Header> headers() {
        return List.of(new RecordHeader(
                EVENT_SOURCE_HEADER, "scanner".getBytes(StandardCharsets.UTF_8)));
    }
}