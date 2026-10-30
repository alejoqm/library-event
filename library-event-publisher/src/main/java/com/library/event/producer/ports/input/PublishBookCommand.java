package com.library.event.producer.ports.input;

import com.library.event.producer.domain.LibraryEventException;

/**
 * Validates and publishes a book event.
 * @param title
 * @param author
 * @param isbn
 */
public record PublishBookCommand(String title, String author, String isbn) {

  /**
   * Validate publish book command.
   * @param title
   * @param author
   * @param isbn
   */
  public PublishBookCommand {
    if (title == null || title.isBlank()) {
      throw new LibraryEventException(
          LibraryEventException.ErrorCode.VALIDATION_FAILED, "title is required");
    }
    if (author == null || author.isBlank()) {
      throw new LibraryEventException(
          LibraryEventException.ErrorCode.VALIDATION_FAILED, "author is required");
    }
    if (isbn == null || isbn.isBlank()) {
      throw new LibraryEventException(
          LibraryEventException.ErrorCode.VALIDATION_FAILED, "isbn is required");
    }
  }
}
