package com.library.event.producer.domain;

public class LibraryEventException extends RuntimeException {
  private final ErrorCode errorCode;

  public LibraryEventException(ErrorCode errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }

  public enum ErrorCode {
    VALIDATION_FAILED,
    PUBLISH_FAILED,
    BOOK_NOT_FOUND
  }
}
