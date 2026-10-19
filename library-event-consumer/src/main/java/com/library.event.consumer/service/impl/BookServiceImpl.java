package com.library.event.consumer.service.impl;

import com.library.event.consumer.domain.LibraryEvent;
import com.library.event.consumer.entity.BookEntity;
import com.library.event.consumer.factory.BookDatabaseFactory;
import com.library.event.consumer.repository.BookRepository;
import com.library.event.consumer.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Timestamp;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

  @Value("${library.campus}")
  private String campus;

  private BookDatabaseFactory bookDatabaseFactory;

  private ObjectMapper objectMapper;

  public BookServiceImpl(BookDatabaseFactory bookDatabaseFactory) {
    this.objectMapper = new ObjectMapper();
    this.bookDatabaseFactory = bookDatabaseFactory;
  }

  public void processBook(ConsumerRecord consumerRecord) {
    try {
      LibraryEvent libraryEvent = objectMapper
          .readValue(consumerRecord.value().toString(), LibraryEvent.class);
      libraryEvent.getBook().setCampus(campus);
      bookDatabaseFactory.getDatabaseService().processBook(libraryEvent, consumerRecord);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Error processing JSON.");
    }


  }

}
