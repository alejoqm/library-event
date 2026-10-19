package com.library.event.producer.response;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class LibraryEventResponse {

    private UUID id;
    private LibraryEventResponseStatus status;
    private Instant timestamp;
}
