package com.library.event;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.library.event.common.domain.Book;
import com.library.event.common.domain.LibraryEvent;
import com.library.event.producer.controller.LibraryEventController;
import com.library.event.producer.exception.LibraryEventException;
import com.library.event.producer.services.LibraryEventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

@WebMvcTest(LibraryEventController.class)
@AutoConfigureMockMvc
public class LibraryEventControllerUnitTest {

  private static String URL = "/v1/libraryEvent";

  @Autowired
  private MockMvc mockMvc;

  private LibraryEventService libraryEventService;

  private ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void testPostLibraryEvent() throws Exception, LibraryEventException {
    LibraryEvent libraryEvent = LibraryEvent.builder()
        .id(null)
        .book(Book.builder().name("Name").author("Author").build())
        .build();

        mockMvc.perform(post(URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(libraryEvent)))
        .andExpect(status().isCreated());
  }

  @Test
  public void tesPutLibraryEvent() throws Exception {
    LibraryEvent libraryEvent = LibraryEvent.builder()
        .id(UUID.randomUUID())
        .book(Book.builder().name("Name").author("Author").build())
        .build();

    mockMvc.perform(put(URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(libraryEvent)))
        .andExpect(status().isOk());
  }

  @Test
  public void updateLibraryEvent_withNullLibraryEventId() throws Exception {
    LibraryEvent libraryEvent = LibraryEvent.builder()
        .id(null)
        .book(Book.builder().name("Name").author("Author").build())
        .build();

    mockMvc.perform(put(URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(libraryEvent)))
        .andExpect(status().isBadRequest());
  }

}
