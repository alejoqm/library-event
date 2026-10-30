package com.library.event.producer.ports.input;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.library.event.producer.domain.LibraryEventException;
import org.junit.jupiter.api.Test;

public class PublishBookCommandTest {

  @Test
  void shouldThrowWhenTitleIsEmpty() {
    assertThrows(
        LibraryEventException.class,
        () -> {
          new PublishBookCommand("", "author", "isbn");
        });
  }

  @Test
  void shouldThrowWhenIsbnIsNull() {
    assertThrows(
        LibraryEventException.class,
        () -> {
          new PublishBookCommand("title", "author", null);
        });
  }

  @Test
  void shouldThrowWhenAuthorIsBlank() {
    assertThrows(
        LibraryEventException.class,
        () -> {
          new PublishBookCommand("title", " ", "isbn");
        });
  }
}
