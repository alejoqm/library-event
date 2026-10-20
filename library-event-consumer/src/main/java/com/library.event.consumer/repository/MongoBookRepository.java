package com.library.event.consumer.repository;

import com.library.event.consumer.entity.BookMongoDocument;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@Profile("mongo")
public interface MongoBookRepository extends MongoRepository<BookMongoDocument, UUID> {
}