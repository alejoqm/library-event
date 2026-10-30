package com.library.event.producer.adapters.input;

import com.library.event.producer.ports.input.PublishBookCommand;
import com.library.event.producer.ports.input.PublishBookUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublishBookController {

  /** Use case for publish book. */
  private final PublishBookUseCase publishBookUseCase;

  /**
   * Inject use case implementation.
   * @param publishBookUseCase
   */
  public PublishBookController(final PublishBookUseCase publishBookUseCase) {
    this.publishBookUseCase = publishBookUseCase;
  }

  /**
   * Rest method to publish an event with book information.
   * @param publishBookRequest
   * @return
   */
  @PostMapping("/v1/events/book")
  public ResponseEntity<Void> create(@Valid @RequestBody PublishBookRequest publishBookRequest) {
    publishBookUseCase.publish(
        new PublishBookCommand(
            publishBookRequest.title(), publishBookRequest.author(), publishBookRequest.isbn()));
    return ResponseEntity.status(HttpStatus.ACCEPTED).build();
  }
}
