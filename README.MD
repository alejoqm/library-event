# Library Event Streaming

A Kafka-based event streaming service for library operations (book checkouts, returns, etc.). Built with **Spring Boot 4**, **Java 17**, and **Apache Kafka 4.2**.

---

## Architecture

┌──────────────────────────────┐ │ REST API (Port 4420) │ │ ┌──────────────────────┐ │ │ │ LibraryEventController │ │ │ └──────────┬───────────┘ │ │ │ │ │ ┌──────────▼───────────┐ │ │ │ LibraryEventService │ │ │ └──────────┬───────────┘ │ │ │ │ │ ┌──────────▼───────────┐ │ │ │ KafkaPublisher │ │ │ └──────────┬───────────┘ │ └──────────────┼────────────────┘ │ │ UUID Key + JSON Value ▼ ┌──────────────────────────────┐ │ Kafka Topic: library-events │ │ Partitions: 3 │ │ Replicas: 1 │ └──────────────────────────────┘


---

## Project Structure


| Module | Purpose |
|--------|---------|
| `library-event-common` | Shared DTOs, enums, domain entities |
| `library-event-publisher` | REST endpoint, Kafka publisher |
| `library-event-consumer` | Kafka consumer - Persisting with MySql |
| `local/` | Docker Compose for local Kafka, MySql and Mongo |

---

## Prerequisites

- **Java 17+**
- **Docker Desktop** (Windows/Mac/Linux)
- **Maven 3.9+**
- **Postman** or `curl` for testing

---

## Quick Start

### 1. Start Kafka with Docker

```bash
cd local
docker compose up -d

# Verify Kafka is running
docker compose ps

# View logs (optional)
docker compose logs -f kafka

Wait until you see:

[main] INFO org.apache.kafka.server.KafkaBroker - Kafka server started...

2. Build and Run the Publisher
# From the root directory
mvn clean install -DskipTests

# Run the publisher module
cd library-event-publisher
mvn spring-boot:run

# Or run from IDE (LibraryEventProducerApplication.java)

The application will start on http://localhost:4420.

3. Verify Topic Creation
On startup, AutoCreateConfig automatically creates the library-events topic:

# Check topic exists
docker exec library-kafka-broker bin/kafka-topics.sh \
  --bootstrap-server localhost:9092 \
  --describe --topic library-events

Expected output:

Topic: library-events    PartitionCount: 3    ReplicationFactor: 1

API Endpoints
POST /v1/libraryEvent
Publishes a new library event to Kafka.

Request:

POST http://localhost:4420/v1/libraryEvent
Content-Type: application/json

{
    "libraryEventType": "CREATE",
    "book": {
        "name": "Metamorfosis",
        "author": "Franz Kafka"
    }
}

Response:

{
    "id": "bc5a7a92-b7c0-4147-a9f6-aceccc942445",
    "status": "ACCEPTED",
    "timestamp": "2026-10-19T21:33:58.383341500Z"
}

Field	Type	Description
id	UUID	Generated event ID (becomes Kafka key)
status	String	ACCEPTED (processing in background)
timestamp	ISO-8601	When the event was submitted
Test with Postman
Open Postman
Create new POST request: http://localhost:4420/v1/libraryEvent
Set Headers: Content-Type: application/json
Set Body (raw JSON):
{
    "libraryEventType": "CREATE",
    "book": {
        "name": "Metamorfosis",
        "author": "Franz Kafka"
    }
}

Click Send
Expected Response:

{
    "id": "bc5a7a92-b7c0-4147-a9f6-aceccc942445",
    "status": "ACCEPTED",
    "timestamp": "2026-10-19T21:33:58.383341500Z"
}

Test with cURL
curl -X POST http://localhost:4420/v1/libraryEvent \
  -H 'Content-Type: application/json' \
  -d '{
    "libraryEventType": "CREATE",
    "book": {
        "name": "Metamorfosis",
        "author": "Franz Kafka"
    }
  }'

Monitor Events in Kafka UI
Optionally add kafka-ui to docker-compose.yaml:

services:
  kafka-ui:
    image: provectuslabs/kafka-ui:latest
    ports:
      - "8080:8080"
    environment:
      KAFKA_CLUSTERS_0_NAME: library-kafka
      KAFKA_CLUSTERS_0_BOOTSTRAPSERVERS: kafka:9093
    depends_on:
      kafka:
        condition: service_healthy

Then visit: http://localhost:8080

Browse topics, view messages, and monitor consumer groups.

### Stack

| Component | Version |
|---|---|
| Spring Boot | 4.1.1 |
| Java | 17 |
| Apache Kafka (broker image) | 4.2.2 |
| Kafka client (via Boot BOM) | 4.2.1 |
| Jackson | 3 (`tools.jackson`) |
| Maven | 3.9+ (enforced by `maven-enforcer-plugin`) |

Future Work
 Implement Junit in differnt components
 Implement Save in Mongo in (library-event-consumer)
 Add integration tests with embedded Kafka
 Support multiple event types (UPDATE, DELETE)

License
MIT License - Feel free to use for learning and personal projects.

Author
AlejoQM