package com.library.event.producer.domain;

public class LibraryEventException extends RuntimeException {

  /** Error code to translate to HTTPStatus */
  private final ErrorCode errorCode;

  /**
   * Setting error code and message.
   * @param errorCode
   * @param message
   */
  public LibraryEventException(ErrorCode errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  /**
   * Returning get Error code.
   * @return
   */
  public ErrorCode getErrorCode() {
    return errorCode;
  }

  public enum ErrorCode {
    VALIDATION_FAILED,
    PUBLISH_FAILED,
    BOOK_NOT_FOUND
  }
}
