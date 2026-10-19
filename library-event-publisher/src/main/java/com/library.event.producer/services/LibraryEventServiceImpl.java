package com.library.event.producer.services;

import com.library.event.common.domain.LibraryEvent;
import com.library.event.producer.response.LibraryEventResponse;
import com.library.event.producer.response.LibraryEventResponseStatus;
import com.library.event.producer.exception.LibraryEventException;
import com.library.event.producer.messaging.LibraryEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class LibraryEventServiceImpl implements LibraryEventService{

  private final LibraryEventPublisher libraryEventPublisher;

  public LibraryEventServiceImpl(LibraryEventPublisher libraryEventPublisher) {
    this.libraryEventPublisher = libraryEventPublisher;
  }

  @Override
  public LibraryEventResponse sent(LibraryEvent libraryEvent) throws LibraryEventException {
    try {
      
      LibraryEventResponse response = LibraryEventResponse.builder()
              .id(UUID.randomUUID())
              .status(LibraryEventResponseStatus.ACCEPTED)
              .timestamp(Instant.now())
              .build();
      
      this.libraryEventPublisher.publish(libraryEvent);
      return response;
    } catch (Exception e) {
      throw new LibraryEventException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }
}
