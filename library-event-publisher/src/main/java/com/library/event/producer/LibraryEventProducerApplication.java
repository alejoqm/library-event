package com.library.event.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryEventProducerApplication {

  /**
   * Private constructor to avoid default one.
   */
  private LibraryEventProducerApplication() {

  }

  /**
   * Main class to start Producer.
   * @param args
   */
  public static void main(String[] args) {
    SpringApplication.run(LibraryEventProducerApplication.class, args);
  }
}
