package com.library.event.producer.adapters.input;

import com.library.event.producer.domain.LibraryEventException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Handles exceptions thrown by event processing handlers. Maps domain/business exceptions to
 * appropriate HTTP responses.
 */
@ControllerAdvice
public final class GlobalExceptionHandler {

  /** Logger for this class. */
  private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  /**
   * Handling Library Event Exception (Domain Exception)
   * @param e
   * @return wrapper of ApiError
   */
  @ExceptionHandler(LibraryEventException.class)
  public ResponseEntity<ApiError> libraryEventExceptionHandler(final LibraryEventException e) {
    LOG.warn("Business exception: {} - {}", e.getErrorCode(), e.getMessage());
    return ResponseEntity.status(getStatusCode(e.getErrorCode()))
        .body(new ApiError(e.getErrorCode().name(), e.getMessage()));
  }

  /**
   * Handling Validation Exception.
   * @param ex
   * @return wrapper with ApiError
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleValidation(final MethodArgumentNotValidException ex) {
    String field =
        ex.getBindingResult().getFieldErrors().stream()
            .findFirst()
            .map(e -> e.getField())
            .orElse("request");
    String message =
        ex.getBindingResult().getFieldErrors().stream()
            .findFirst()
            .map(e -> e.getDefaultMessage())
            .orElse("Invalid input");

    return ResponseEntity.badRequest().body(new ApiError(field, message));
  }

  private HttpStatus getStatusCode(LibraryEventException.ErrorCode errorCode) {
    return switch (errorCode) {
      case VALIDATION_FAILED -> HttpStatus.BAD_REQUEST;
      case BOOK_NOT_FOUND -> HttpStatus.NOT_FOUND;
      case PUBLISH_FAILED -> HttpStatus.INTERNAL_SERVER_ERROR;
    };
  }
}
