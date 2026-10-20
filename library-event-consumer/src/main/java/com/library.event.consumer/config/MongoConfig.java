package com.library.event.consumer.config;

import com.library.event.consumer.repository.MongoBookRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

//@Configuration
@Profile("mongo")
public class MongoConfig {

  /*
    @Bean
    public BookRepository bookRepository(MongoTemplate template) {
        return new MongoBookRepository(template);
    }

   */
}
