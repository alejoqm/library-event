package com.library.event.consumer.service;

import com.library.event.common.domain.LibraryEvent;

public interface BookService {
  void processBook(LibraryEvent libraryEvent);
}
