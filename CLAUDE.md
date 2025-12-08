# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build Commands

```bash
# Build entire project (from root)
mvn clean install

# Build a specific module
cd <module-directory> && mvn clean package

# Build without tests
mvn clean package -DskipTests

# Run a specific service
cd <module-directory> && mvn spring-boot:run
```

## Docker Commands

```bash
# Start all services with Docker Compose
docker-compose up -d

# Build and run a specific service
docker build -t <service-name> ./<module-directory>
```

## Service Ports

- **registry** (Eureka): 8761
- **config**: 8081
- **gateway**: 8080
- **propertyAcqCenter**: 9930
- **cloudBridges**: 8097

## Architecture

This is a Spring Boot microservices backend using:
- **Java 17** with **Spring Boot 3.2.6** and **Spring Cloud 2023.0.4**
- **Eureka** for service discovery (registry module)
- **Spring Cloud Config** for centralized configuration (config module)
- **Spring Cloud Gateway** for API gateway
- **PostgreSQL** as database
- **Keycloak** for authentication (identity module)
- **Kafka** for event-driven messaging
- **Redis** for caching
- **RabbitMQ** (AMQP) for messaging
- **Liquibase** for database migrations (propertyAcqCenter)

### Module Structure

Active modules in parent pom.xml:
- `config` - Centralized configuration server
- `gateway` - API Gateway with Keycloak integration
- `registry` - Eureka service discovery
- `identity` - Authentication/authorization with Keycloak admin client
- `cloudBridges` - Cloud integrations (S3, Mailjet, Firebase notifications)
- `report` - Report generation
- `propertyAcqCenter` - Main property acquisition business logic

### CQRS Pattern (Command Query Responsibility Segregation)

Services follow CQRS with the `share` module providing core abstractions:
- `ICommand` / `ICommandBus` / `ICommandHandler` - for write operations
- `IQuery` / `IQueryBus` / `IQueryHandler` - for read operations

Each feature is organized as:
```
application/
  command/<feature>/
    create/  - CreateXxxCommand, CreateXxxCommandHandler, CreateXxxRequest, CreateXxxMessage
    update/  - UpdateXxxCommand, UpdateXxxCommandHandler, UpdateXxxRequest
    delete/  - DeleteXxxCommand, DeleteXxxCommandHandler
  query/<feature>/
    getById/ - FindXxxByIdQuery, FindXxxByIdQueryHandler, XxxResponse
    search/  - GetSearchXxxQuery, GetSearchXxxQueryHandler
```

### Layered Architecture (per microservice)

- `controller/` - REST API endpoints
- `application/` - CQRS commands, queries, handlers, and DTOs
- `domain/` - Business rules, DTOs, enums, service interfaces
- `infrastructure/` - JPA entities, repositories, service implementations, configs

### Configuration

Services use Spring Cloud Config with profiles:
- `RC_ACTIVE_PROFILE` env var to set profile (default: `default`)
- `RC_SERVER_CONFIG` env var for config server host (default: `localhost`)
- Configuration stored in centralized config server at port 8081

## Shared Library

The `share-properties-1.0.0.jar` is a local dependency installed via:
```bash
mvn install:install-file \
  -Dfile=./propertyAcqCenter/libs/share-properties-1.0.0.jar \
  -DgroupId=com.knsof \
  -DartifactId=share-properties \
  -Dversion=1.0.0 \
  -Dpackaging=jar \
  -DlocalRepositoryPath=repository \
  -DcreateChecksum=true
```