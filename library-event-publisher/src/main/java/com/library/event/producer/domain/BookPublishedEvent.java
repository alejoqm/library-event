package com.library.event.producer.domain;

import java.time.Instant;
import java.util.UUID;

public record BookPublishedEvent(
    UUID eventId, BookEventType eventType, Instant occurredAt, BookPayload book) {
  public record BookPayload(String title, String author, String isbn) {}
}
