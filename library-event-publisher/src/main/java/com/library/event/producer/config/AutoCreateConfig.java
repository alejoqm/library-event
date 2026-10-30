package com.library.event.producer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class AutoCreateConfig {

  /** Kafka topic name. */
  private final String topicName;

  /** Total of partitions. */
  private final int partitions;

  /**
   * Setting topic name and partitions.
   * @param topicName
   * @param partitions
   */
  public AutoCreateConfig(
      @Value("${spring.kafka.template.default-topic}") String topicName,
      @Value("${kafka.topic.partitions:3}") int partitions) {
    this.topicName = topicName;
    this.partitions = partitions;
  }

  /**
   * Creating Topic bean for kafka.
   * @return
   */
  @Bean
  public NewTopic libraryEvents() {
    return TopicBuilder.name(topicName).partitions(partitions).replicas(1).build();
  }
}
