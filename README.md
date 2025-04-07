# DBRelease - Liquibase Database Versioning Application

A Spring Boot application for managing database schema changes and versioning using Liquibase. This application provides REST APIs for database rollback operations.

## Features

- Database schema versioning using Liquibase
- REST API for rolling back to specific tags
- REST API for rolling back a specific number of changesets
- Proper error handling for database operations

## Project Structure

```
DBRelease/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── db/
│   │   │           └── release/
│   │   │               ├── config/
│   │   │               │   └── DatabaseConfig.java
│   │   │               ├── controller/
│   │   │               │   └── DatabaseRollbackController.java
│   │   │               ├── exception/
│   │   │               │   ├── DatabaseRollbackException.java
│   │   │               │   └── GlobalExceptionHandler.java
│   │   │               ├── service/
│   │   │               │   └── DatabaseRollbackService.java
│   │   │               └── DbReleaseApplication.java
│   │   └── resources/
│   │       ├── db/
│   │       │   └── changelog/
│   │       │       ├── db.changelog-baseline.xml
│   │       │       └── db.changelog-master.xml
│   │       └── application.yml
│   └── test/
│       └── java/
│           └── com/
│               └── db/
│                   └── release/
└── pom.xml
```

## Technologies Used

- Java 17
- Spring Boot 3.4.4
- Liquibase
- PostgreSQL
- Maven
- openapi 2.3.0

## API Endpoints

### Rollback to a specific tag

```
POST /rollback/tag/{tag}
```

Rolls back the database to the specified tag.

### Rollback a specific number of changesets

```
POST /rollback/last/{count}
```

Rolls back the specified number of most recent changesets.

## Building and Running

### Prerequisites

- Java 17 or higher
- Maven
- PostgreSQL database

### Build

```bash
mvn clean package
```

### Run

```bash
java -jar target/DBRelease-0.0.1-SNAPSHOT.jar
```

Or using Maven:

```bash
mvn spring-boot:run
```

## Database Configuration

The application is configured to connect to a PostgreSQL database. The connection details are specified in the `application.yml` file.

## Liquibase Configuration

Liquibase is configured to use the changelog files located in the `src/main/resources/db/changelog` directory. The master changelog file is `db.changelog-master.xml`.