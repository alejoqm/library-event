package com.library.event.producer.adapters.input;

import com.library.event.producer.domain.LibraryEventException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(LibraryEventException.class)
  public ResponseEntity<ApiError> libraryEventExceptionHandler(LibraryEventException e) {
    log.warn("Business exception: {} - {}", e.getErrorCode(), e.getMessage());
    return ResponseEntity
            .status(getStatusCode(e.getErrorCode()))
            .body(new ApiError(e.getErrorCode().name(), e.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {
    String field = ex.getBindingResult().getFieldErrors().stream()
            .findFirst()
            .map(e -> e.getField())
            .orElse("request");
    String message = ex.getBindingResult().getFieldErrors().stream()
            .findFirst()
            .map(e -> e.getDefaultMessage())
            .orElse("Invalid input");

    return ResponseEntity.badRequest()
            .body(new ApiError(field, message));
  }

  private HttpStatus getStatusCode(LibraryEventException.ErrorCode errorCode) {
    return switch (errorCode) {
      case VALIDATION_FAILED -> HttpStatus.BAD_REQUEST;
      case BOOK_NOT_FOUND    -> HttpStatus.NOT_FOUND;
      case PUBLISH_FAILED    -> HttpStatus.INTERNAL_SERVER_ERROR;
    };
  }
}
