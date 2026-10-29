package com.library.event.producer.ports.input;

import com.library.event.producer.domain.LibraryEventException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PublishBookCommandTest {

    @Test
    void shouldThrowWhenTitleIsEmpty() {
        assertThrows(LibraryEventException.class, () -> {
            new PublishBookCommand("", "author", "isbn");
        });
    }

    @Test
    void shouldThrowWhenIsbnIsNull() {
        assertThrows(LibraryEventException.class, () -> {
            new PublishBookCommand("title", "author", null);
        });
    }

    @Test
    void shouldThrowWhenAuthorIsBlank() {
        assertThrows(LibraryEventException.class, () -> {
            new PublishBookCommand("title", " ", "isbn");
        });
    }

}
