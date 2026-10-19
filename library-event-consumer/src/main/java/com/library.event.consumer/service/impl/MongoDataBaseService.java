package com.library.event.consumer.service.impl;

import com.library.event.consumer.domain.LibraryEvent;
import com.library.event.consumer.repository.MongoDBRepository;
import com.library.event.consumer.service.DatabaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MongoDataBaseService implements DatabaseService {

  private MongoDBRepository mongoDBRepository;

  @Autowired(required = false)
  public void setMongoDBRepository(MongoDBRepository mongoDBRepository) {
    this.mongoDBRepository = mongoDBRepository;
  }

  @Override
  public void processBook(LibraryEvent libraryEvent, ConsumerRecord consumerRecord) {
    log.info("Saving book in MongoDb {}", libraryEvent.getBook().getName());
    mongoDBRepository.saveBook(libraryEvent);
  }
}
