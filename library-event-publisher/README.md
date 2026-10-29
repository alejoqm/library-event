# Library Event Producer

A Spring Boot application implementing Hexagonal Architecture to publish book event information to a Kafka topic via REST API.

## Architecture Overview

This project follows Hexagonal (Ports & Adapters) Architecture:

- **Domain**: Core business logic and entities
- **Ports**: Interfaces defining contracts between domain and external systems
    - Input Ports: Use cases that the application exposes
    - Output Ports: Interfaces for external system interactions
- **Adapters**: Implementations of ports
    - Input Adapters: REST controllers that translate HTTP requests to use case commands
    - Output Adapters: Kafka producers that implement event publishing

## Technology Stack

| Component | Version |
|-----------|---------|
| Java | 21 |
| Spring Boot | 4.1.1 |
| Kafka Clients | 4.2.1 |

## Project Structure

```
com.library.event.producer
├── adapters
│   ├── input          # REST controllers, request DTOs, exception handling
│   └── output         # Kafka producer implementations
├── application        # Use case implementations
├── config             # Application configuration
├── domain             # Core entities, events, exceptions
└── ports
    ├── input          # Use case interfaces
    └── output         # Service interfaces for external systems
```

## Prerequisites

- JDK 21 or higher
- Maven 3.6+
- Docker and Docker Compose (for Kafka infrastructure)

## Running Kafka

### Using Docker Compose (Included in Repo)

The repository includes a `docker-compose.yml` file with Kafka infrastructure:

```bash
docker-compose up -d
```

This will start:
- Zookeeper on port 2181
- Kafka broker on port 9092

### Creating Kafka Topic

Before running the application, create the library events topic:

```bash
./bin/kafka-topics.sh --create --topic library-events --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
```

Or using Docker:

```bash
docker-compose exec kafka ./bin/kafka-topics.sh --create --topic library-events --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
```

## Running the Application

### From IDE

Run `LibraryEventProducerApplication.java` as a Spring Boot application.

### From Command Line

```bash
mvn spring-boot:run
```

To build and run:

```bash
mvn clean package
java -jar target/library-event-producer-0.0.1-SNAPSHOT.jar
```

The application will start on `http://localhost:4420`

## API Endpoints

### Publish Book Event

**POST** `/v1/events/book`

Publishes a book event to Kafka.

**Request Headers:**
```
Content-Type: application/json
```

**Request Body:**
```json
{
  "title": "Metamorfosis",
  "author": "Franz Kafka",
  "isbn": "9780134685991"
}
```

**Response:**
- `201 Created` - Event successfully published
- `400 Bad Request` - Invalid request body
- `500 Internal Server Error` - Publishing failed

**cURL Example:**
```bash
curl --location --request POST 'http://localhost:4420/v1/events/book' \
--header 'Content-Type: application/json' \
--data-raw '{
  "title": "Metamorfosis",
  "author": "Franz Kafka",
  "isbn": "9780134685991"
}'
```

### Verify Message in Kafka

Using Docker:

```bash
docker-compose exec kafka ./bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic library-events --from-beginning
```

## Key Components

### Domain Layer
- `BookEventType`: Enumeration defining supported event types
- `BookPublishedEvent`: Value object representing a book publication event
- `LibraryEventException`: Custom exception for domain errors

### Application Layer
- `DefaultPublishBookUseCase`: Implementation of the book publishing use case

### Ports
- `PublishBookUseCase`: Input port interface for publishing books
- `PublishBookCommand`: Command object for the use case
- `BookEventPublisher`: Output port interface for event publishing

### Adapters
- `PublishBookController`: Input adapter (REST API endpoint)
- `BookEventProducerAdapter`: Output adapter (Kafka producer)
- `GlobalExceptionHandler`: Centralized exception handling
- `ApiError`: Error response DTO

## Configuration

Main configuration is in `src/main/resources/application.yml` or `application.properties`:

```yaml
spring:
  kafka:
    bootstrap-servers: localhost:9092
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer

server:
  port: 4420
```

## Testing

```bash
mvn test
```


## Building

```bash
mvn clean package -DskipTests
```

Run the JAR:

```bash
java -jar target/library-event-producer-0.0.1-SNAPSHOT.jar
```

## Error Handling

The application implements global exception handling with standardized error responses:
- Validation errors return `400 Bad Request`
- Publishing failures return `500 Internal Server Error`
- All errors include detailed messages in the response body

## Development

### Dependencies

Project uses Maven for dependency management. Main dependencies include:

- Spring Boot 4.1.1 starters (web, kafka, data-jpa, jackson)
- Kafka Clients 4.2.1

### Code Quality

```bash
mvn spotless:check
mvn checkstyle:check
```

## Troubleshooting

### Common Issues

1. **Port 4420 already in use**: Modify `application.yml` to use a different port
2. **Kafka connection refused**: Ensure Docker containers are running
3. **Topic does not exist**: Create the topic before publishing events

### Logs

Enable debug logging in `application.yml`:

```yaml
logging:
  level:
    com.library.event.producer: DEBUG
```

## Author

Alejoqm
