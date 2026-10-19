package com.library.event.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class LibraryEvent {

  private UUID id;
  private LibraryEventType libraryEventType;
  private Book book;
}
