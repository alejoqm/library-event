package com.library.event.consumer.service.impl;

import com.library.event.common.domain.Book;
import com.library.event.common.domain.LibraryEvent;
import com.library.event.consumer.entity.BookJpaEntity;
import com.library.event.consumer.repository.BookJpaRepository;
import com.library.event.consumer.service.BookService;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("mysql")
@RequiredArgsConstructor
@Slf4j
public class MySqlBookService implements BookService {

  private final BookJpaRepository repository;

  @Override
  public void processBook(LibraryEvent event) {
    Book book = event.getBook();
    if (book == null) {
      log.warn("Event {} has no book, skipping", event.getId());
      return;
    }

    UUID id = event.getId() != null ? event.getId() : UUID.randomUUID();

    BookJpaEntity entity = BookJpaEntity.builder()
            .id(id)
            .name(book.getName())
            .author(book.getAuthor())
            .campus(book.getCampus())
            .createdAt(Instant.now())
            .build();

    repository.save(entity);
    log.info("Saved book '{}' ({}) to MySQL", entity.getName(), entity.getId());
  }
}