# Library Event Consumer

Kafka consumer that processes library events and persists books to MySQL or MongoDB.

---

## Prerequisites

- **Java 17+**
- **Maven 3.9+**
- **Docker Desktop** (for Kafka, MySQL, MongoDB)

---

## Running Kafka, MySQL, and MongoDB

All three services run together from the repository root:

```bash
cd local
docker compose up -d

Or with JAR:

Verifying all containers are running:

docker compose ps

Expected: kafka, mysql, and mongodb all show Up.

Building the Consumer
From the repository root:

mvn clean install -DskipTests

Running the Consumer
MySQL Mode
cd library-event-consumer
mvn spring-boot:run -Dspring-boot.run.profiles=mysql

Or with JAR:

java -jar target/library-event-consumer-1.0.0-SNAPSHOT.jar --spring.profiles.active=mysql

MongoDB Mode
cd library-event-consumer
mvn spring-boot:run -Dspring-boot.run.profiles=mongo

Or with JAR:

java -jar target/library-event-consumer-1.0.0-SNAPSHOT.jar --spring.profiles.active=mongo

Configuration
Properties File
Property	Value	Purpose
server.port	4421	Consumer API port (change if needed)
spring.profiles.active	mysql or mongo	Select database backend
spring.kafka.consumer.group-id	library-consumer-group	Consumer group ID
spring.kafka.consumer.auto-offset-reset	earliest	Start from oldest message
Profile-Specific Files
application-mysql.properties — MySQL connection settings
application-mongo.properties — MongoDB connection settings
Switching databases at runtime: Change spring.profiles.active in application.properties or use -Dspring-boot.run.profiles=<profile>.

Testing
1. Publish a Test Event
Use the publisher endpoint:

curl -X POST http://localhost:4420/v1/libraryEvent \
  -H 'Content-Type: application/json' \
  -d '{
    "libraryEventType": "CREATE",
    "book": {
      "name": "Metamorfosis",
      "author": "Franz Kafka"
    }
  }'

2. Verify Persistence
MySQL:

docker exec -it library-mysql mysql -ulibraryuser -plibrarypass library_events -e "SELECT * FROM book;"

MongoDB:

docker exec -it library-mongo mongosh library_events --eval "db.books.find()"

Consumer Group Management
List Consumer Groups
docker exec library-kafka-broker bin/kafka-consumer-groups.sh \
  --bootstrap-server localhost:9092 \
  --list

Describe Consumer Group (Check Offsets)
docker exec library-kafka-broker bin/kafka-consumer-groups.sh \
  --bootstrap-server localhost:9092 \
  --describe --group library-consumer-group

Reset Consumer Group (Start Over)
docker exec library-kafka-broker bin/kafka-consumer-groups.sh \
  --bootstrap-server localhost:9092 \
  --reset-offsets --to-earliest \
  --group library-consumer-group \
  --topic library-events \
  --execute

Architecture
┌─────────────────────┐
│    Kafka Topic      │
│  library-events     │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│  LibraryEventConsumer│
│  (@KafkaListener)   │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│   BookService       │
│   (Profile-based)   │
└──────────┬──────────┘
           │
    ┌──────┴──────┐
    ▼             ▼
┌─────────┐   ┌──────────┐
│ MySQL   │   │ MongoDB  │
│ JPA     │   │ Document │
└─────────┘   └──────────┘

Troubleshooting
Consumer Not Processing Messages
Check auto-offset-reset=earliest in properties
Reset consumer group offset (see above)
Verify topic name matches (library-events)
Database Connection Error
Ensure Docker containers are running: docker compose ps
Check credentials match application-{mysql,mongo}.properties
Jackson Serialization Error
Make sure Jackson 2 dependencies are present in pom.xml (required by kafka-clients):

<dependency>
  <groupId>com.fasterxml.jackson.core</groupId>
  <artifactId>jackson-databind</artifactId>
  <version>2.17.0</version>
</dependency>

Stop the Consumer
Press Ctrl+C in the terminal.

To stop all Docker services:

cd local
docker compose down

---