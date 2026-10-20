package com.library.event.consumer.repository;

import com.library.event.consumer.entity.BookJpaEntity;
import java.util.UUID;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("mysql")
public interface BookJpaRepository extends JpaRepository<BookJpaEntity, UUID> {
}