package com.library.event.producer.controller;

import com.library.event.common.domain.LibraryEvent;
import com.library.event.common.domain.LibraryEventType;
import com.library.event.producer.response.LibraryEventResponse;
import com.library.event.producer.exception.LibraryEventException;
import com.library.event.producer.services.LibraryEventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LibraryEventController {

  private final LibraryEventService libraryEventService;

  public LibraryEventController(final LibraryEventService libraryEventService) {
    this.libraryEventService = libraryEventService;
  }

  @PostMapping("/v1/libraryEvent")
  public ResponseEntity<LibraryEventResponse> create(@RequestBody LibraryEvent libraryEvent)
      throws LibraryEventException {
    libraryEvent.setLibraryEventType(LibraryEventType.CREATE);
    return ResponseEntity.status(HttpStatus.CREATED).body(libraryEventService.sent(libraryEvent));
  }

  @PutMapping("/v1/libraryEvent")
  public ResponseEntity<LibraryEvent> update(@RequestBody LibraryEvent libraryEvent)
      throws LibraryEventException {

    if(libraryEvent.getId() == null) {
      throw new LibraryEventException(HttpStatus.BAD_REQUEST, "Id is required.");
    }

    libraryEvent.setLibraryEventType(LibraryEventType.UPDATE);
    libraryEventService.sent(libraryEvent);
    return ResponseEntity.status(HttpStatus.OK).body(libraryEvent);
  }
}
