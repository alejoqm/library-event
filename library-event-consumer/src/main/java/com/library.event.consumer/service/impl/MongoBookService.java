package com.library.event.consumer.service.impl;

import com.library.event.common.domain.LibraryEvent;
import com.library.event.consumer.repository.MongoBookRepository;
import com.library.event.consumer.service.BookService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MongoBookService implements BookService {

  private MongoBookRepository mongoBookRepository;

  public void setMongoDBRepository(MongoBookRepository mongoBookRepository) {
    this.mongoBookRepository = mongoBookRepository;
  }

  @Override
  public void processBook(LibraryEvent libraryEvent) {
    log.info("Saving book in MongoDb {}", libraryEvent.getBook().getName());
  }
}
