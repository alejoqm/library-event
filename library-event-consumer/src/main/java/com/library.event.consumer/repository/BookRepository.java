package com.library.event.consumer.repository;

import com.library.event.consumer.entity.BookEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Profile("mysql")
@Repository
public interface BookRepository extends CrudRepository<BookEntity, Integer> {

}
