package com.library.event.producer.adapters.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PublishBookRequest(@NotBlank String title,
                                 @NotBlank String author,
                                 @NotBlank @Pattern(regexp = "^(97(8|9))?\\d{9}(\\d|X)$") String isbn) {
}
