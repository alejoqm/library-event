package com.library.event.consumer.service;

import com.library.event.consumer.domain.LibraryEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface DatabaseService {

  void processBook(LibraryEvent libraryEvent, ConsumerRecord consumerRecord);
}
