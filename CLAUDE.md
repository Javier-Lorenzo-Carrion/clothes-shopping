# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Quick Start Commands

### Building and Running
- **Build the project**: `./gradlew build`
- **Run the application**: `./gradlew bootRun`
- **Run tests**: `./gradlew test`
- **Run a single test**: `./gradlew test --tests ClassName` (e.g., `--tests UserServiceTest`)
- **Run integration tests only**: `./gradlew test --tests "*IntegrationTest"`
- **Run unit tests only**: `./gradlew test --tests "*Test" -x "*IntegrationTest"`

### Development Environment
- **Start local PostgreSQL**: `docker-compose up -d`
- **Stop PostgreSQL**: `docker-compose down`
- **View logs**: `./gradlew bootRun | tail -f`
- **Check test coverage**: `./gradlew test jacocoTestReport`

### Linting and Code Quality
- **Format code**: `./gradlew spotlessApply` (if available) or IDE formatting
- **Check formatting**: `./gradlew spotlessCheck` (if available)

## Architecture Overview

This is a **Spring Boot REST API** for user management in a clothes shopping application. It follows **layered hexagonal architecture** with clear separation of concerns:

```
REST API Layer (UserController)
    ↓
Application Service Layer (UserService)
    ↓
Domain Model Layer (User, UserRepository interface)
    ↓
Infrastructure Layer (PostgreUserRepository, UserEntity, Database)
```

### Key Architectural Patterns

1. **Domain-Driven Design (DDD)**:
   - Core domain logic in `User` entity with factory method `User.create()`
   - Value objects as Java records: `CreatableUserFields`, `EditableUserFields`
   - Repository pattern with domain interface (`UserRepository`) and infrastructure implementation (`PostgreUserRepository`)
   - Domain exceptions: `InvalidUserException`, `UserNotFoundException`

2. **Dependency Injection**:
   - Constructor-based injection for loose coupling
   - All services use final fields and require explicit dependencies

3. **DTO Pattern**:
   - HTTP request DTOs: `CreateUserRequest`, `UpdateUserRequest`
   - HTTP response DTO: `UserResponse`
   - Conversion: Request → Field records → Domain objects → Response

4. **Validation**:
   - Creation validation in `User.create()` factory method
   - Email format validation (RFC 5321/5322 regex)
   - Birth date format validation (dd/MM/yyyy)
   - **FIXME**: Update validation is incomplete (see `User.java` line 43)

5. **Entity Mapping**:
   - `UserEntity` (JPA) ↔ `User` (domain) via `fromUser()` and `toUser()` methods
   - UUID string handling in domain layer; UUID type in persistence layer

### Directory Structure

```
src/main/java/com/lorenzoconsultores/clothesshopping/
├── business/
│   ├── application/         # Service orchestration layer
│   │   └── UserService.java
│   └── domain/              # Core business logic and interfaces
│       ├── User.java
│       ├── CreatableUserFields.java
│       ├── EditableUserFields.java
│       ├── UserRepository.java (interface)
│       ├── InvalidUserException.java
│       └── UserNotFoundException.java
└── infrastructure/
    ├── persistence/         # Database and ORM layer
    │   ├── entities/UserEntity.java
    │   ├── jpa/UserJPARepository.java
    │   └── postgres/PostgreUserRepository.java
    └── rest/                # HTTP layer
        ├── user/
        │   ├── UserController.java
        │   ├── CreateUserRequest.java
        │   ├── UpdateUserRequest.java
        │   └── UserResponse.java
        └── error/
            ├── ErrorController.java (global exception handler)
            └── ErrorResponse.java
```

### REST API Endpoints

- `POST /users` → Create new user (returns 201 Created, empty body)
- `GET /users` → Retrieve all users (returns array of `UserResponse`)
- `PATCH /users/{id}` → Update user (returns 204 No Content)
- **TODO**: DELETE and GET by ID endpoints not yet implemented (see `UserController.java` line 38)

## Database

### Setup
- PostgreSQL 16.0 (configured in `docker-compose.yaml`)
- Database name: `clothes-shopping` (test: `clothes-shopping-test`)
- Local credentials: user=`test`, password=`culaso`

### Schema Management
- Liquibase handles schema versioning and migrations
- Master changelog: `src/main/resources/liquibase/master.xml`
- Initial schema: `src/main/resources/liquibase/changelogs/20230917_added_users_table.xml`
- Migrations run automatically on application startup

### Configuration
- **Main profile** (`application.yaml`): `jdbc:postgresql://localhost:5432/clothes-shopping`
- **Test profile** (`application-test.yaml`): `jdbc:postgresql://localhost:5432/clothes-shopping-test`

## Testing Strategy

### Unit Tests
- Located in `src/test/java/.../business/application/`
- Example: `UserServiceTest`
- Use `@Nested` classes to group tests by behavior
- Mock the `UserRepository` using Mockito
- Verify business logic in isolation

### Integration Tests
- Located in `src/test/java/.../infrastructure/rest/`
- Example: `UserControllerIntegrationTest`
- Use custom `@IntegrationTest` annotation
- Tests against real database (test profile)
- Clean up after each test with `@AfterEach`

### Running Tests
```bash
./gradlew test                                    # All tests
./gradlew test --tests UserServiceTest            # Single class
./gradlew test --tests UserServiceTest.NestedName # Nested class
./gradlew test --tests "*IntegrationTest"         # Pattern matching
```

## CI/CD Pipeline

- **File**: `.github/workflows/gradle.yml`
- **Trigger**: Push or PR to main branch
- **Runtime**: Java 17 (Corretto) with PostgreSQL 16.0 test container
- **Steps**: Compile → Test → Build JAR

## Common Development Tasks

### Adding a New Endpoint
1. Add method to `UserController` with `@PostMapping`, `@GetMapping`, etc.
2. Create request/response DTOs if needed (in `infrastructure/rest/user/`)
3. Add business logic to `UserService`
4. Write unit tests for service logic
5. Write integration tests for controller behavior
6. Handle exceptions via `ErrorController`

### Adding a New Domain Model
1. Create domain entity in `business/domain/`
2. Create JPA entity in `infrastructure/persistence/entities/`
3. Create repository interface in `business/domain/`
4. Create repository implementation in `infrastructure/persistence/postgres/`
5. Create Liquibase migration for database schema
6. Add service in `business/application/`
7. Add controller in `infrastructure/rest/`

### Adding Database Migration
1. Create new XML changelog in `src/main/resources/liquibase/changelogs/`
2. Use naming convention: `YYYYMMDD_description.xml`
3. Reference in `master.xml`
4. Run application to apply migration automatically

### Debugging
- Add breakpoints in IDE
- Run with `./gradlew bootRun` in debug mode
- Check logs in console output
- Use `@Transactional` carefully in tests to avoid transaction issues

## Code Style and Conventions

- **Language**: Java 17+
- **Build Tool**: Gradle (Kotlin DSL in `build.gradle.kts`)
- **Framework**: Spring Boot 3.1.3
- **Naming**: camelCase for Java, snake_case for SQL/XML
- **Records**: Use Java records for immutable DTOs and value objects
- **Exceptions**: Create domain-specific exceptions; avoid generic `Exception`
- **Validation**: Prefer fail-fast validation in factory methods or constructors

## Known Issues and TODOs

1. **Missing Validation**: Update endpoint lacks email/birth date format validation (see `User.java:43`)
2. **Incomplete Endpoints**: DELETE and GET (by ID) not implemented (see `UserController.java:38`)
3. **Plain-text Credentials**: Database credentials in YAML (fine for local dev, need env vars for production)

## Key Dependencies

- **Spring Boot**: 3.1.3 (core framework)
- **Spring Data JPA**: Latest (ORM and data access)
- **PostgreSQL JDBC Driver**: Latest (database connectivity)
- **Liquibase**: 4.23.2 (database migrations)
- **JUnit 5**: Via Spring Test
- **Mockito**: 3.+ (mocking)
- **AssertJ**: 3.11.1 (fluent assertions)
