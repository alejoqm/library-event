package com.library.event.producer.services;

import com.library.event.common.domain.LibraryEvent;
import com.library.event.producer.response.LibraryEventResponse;
import com.library.event.producer.exception.LibraryEventException;

public interface LibraryEventService {
  LibraryEventResponse sent(LibraryEvent libraryEvent) throws LibraryEventException;
}
