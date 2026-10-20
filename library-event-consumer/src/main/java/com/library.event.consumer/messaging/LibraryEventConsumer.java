package com.library.event.consumer.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.event.common.domain.LibraryEvent;
import com.library.event.consumer.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class LibraryEventConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final BookService bookService;


    /**
     * Handles incoming library events from Kafka.
     * Spring handles automatic acknowledgment by default.
     * For manual acks, inject Acknowledgment as shown below.
     */
    @KafkaListener(
            topics = "${library-event-consumer.topic:library-events}",
            groupId = "${library-event-consumer.group-id:library-consumer-group}"
    )
    public void handleEvent(String event) {
        log.info("Received event with ID: {}", event);
        try {
            bookService.processBook(objectMapper.readValue(event, LibraryEvent.class));
        } catch (JsonProcessingException e) {
            log.error("Error processing event {}", event, e);
        }
    }

}
