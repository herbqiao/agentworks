<div align="center">

# AgentWorks

[![Java](https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.5-6DB33F?logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![MyBatis-Plus](https://img.shields.io/badge/MyBatis--Plus-3.5.7-2D8CF0)](https://baomidou.com/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Maven](https://img.shields.io/badge/Maven-Build-C71A36?logo=apachemaven&logoColor=white)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

**Spring Boot backend foundation for orchestrating AI agent teams in a SaaS development platform**

[Repository](https://github.com/herbqiao/agentworks) · [Issues](https://github.com/herbqiao/agentworks/issues) · [Pull Requests](https://github.com/herbqiao/agentworks/pulls)

</div>

## Screenshot

<!-- Add a screenshot of your app here -->
<!-- ![Screenshot](screenshot.png) -->

## About

AgentWorks is a backend-first SaaS project for running AI agent teams around software delivery. The current repository focuses on the initialization milestone: a Spring Boot 3.2 service skeleton, core domain tables, and the first project/agent management APIs.

At this stage, the repository already provides:

| Capability | Current status |
| --- | --- |
| Project management | Create projects and query a user's project list |
| Agent management | Add AI agent members under a project |
| Persistence foundation | MyBatis-Plus entities/mappers for `aw_user`, `aw_project`, `aw_agent`, `aw_task`, `aw_execution_log` |
| API contract | Unified `Result<T>` response wrapper and global exception handling |
| Cross-cutting model | Audit fields, logical deletion, enum-based domain states |
| API docs | Knife4j / SpringDoc integration |
| Test support | H2-backed integration tests in MySQL compatibility mode |

## Tech Stack

| Category | Technology |
| --- | --- |
| Language | Java 21 |
| Framework | Spring Boot 3.2.5 |
| Persistence | MyBatis-Plus 3.5.7 |
| Database | MySQL 8.0 |
| Connection Pool | HikariCP |
| API Documentation | Knife4j + SpringDoc OpenAPI 3 |
| Build Tool | Maven |
| Utilities | Lombok, MapStruct |
| Testing | Spring Boot Test, H2 |

## Architecture

```text
                +------------------------------+
                |   Web / IM / Ops Clients     |
                |  (future UI, WeChat, DingTalk)|
                +---------------+--------------+
                                |
                                v
                    +------------------------+
                    | Spring Boot REST APIs  |
                    | controller + validation|
                    +-----------+------------+
                                |
                                v
                    +------------------------+
                    | Application Services   |
                    | ProjectService         |
                    | AgentService           |
                    +-----------+------------+
                                |
                                v
                    +------------------------+
                    | MyBatis-Plus Mappers   |
                    | Base CRUD + queries    |
                    +-----------+------------+
                                |
                                v
                    +------------------------+
                    | MySQL aw_* tables      |
                    | user/project/agent/... |
                    +-----------+------------+
                                |
                                v
                    +------------------------+
                    | Future AI integrations |
                    | model gateway / logs   |
                    +------------------------+
```

## Project Structure

```text
agentworks/
├── .github/
│   └── copilot-instructions.md
├── src/
│   ├── main/
│   │   ├── java/com/agentworks/
│   │   │   ├── common/           # Result wrapper, enums, exception handling
│   │   │   ├── config/           # MyBatis-Plus auto-fill
│   │   │   ├── controller/       # REST endpoints and request DTOs
│   │   │   ├── domain/entity/    # aw_* table mappings
│   │   │   ├── mapper/           # MyBatis-Plus mapper interfaces
│   │   │   ├── service/          # Service contracts and implementations
│   │   │   └── AgentWorksApplication.java
│   │   └── resources/
│   │       └── application.yml
│   └── test/
│       ├── java/com/agentworks/
│       │   ├── common/
│       │   ├── config/
│       │   ├── controller/
│       │   └── service/
│       └── resources/
│           ├── application.yml
│           └── schema.sql
├── pom.xml
├── LICENSE
└── README.md
```

## Getting Started

### Prerequisites

- JDK 21
- Maven 3.9+
- MySQL 8.0

### 1. Clone the repository

```bash
git clone https://github.com/herbqiao/agentworks.git
cd agentworks
```

### 2. Create the database

```sql
CREATE DATABASE agentworks DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
```

### 3. Configure environment variables

The application reads datasource settings from `src/main/resources/application.yml` with environment-variable overrides:

| Variable | Description | Default |
| --- | --- | --- |
| `DB_URL` | JDBC URL for MySQL | `jdbc:mysql://127.0.0.1:3306/agentworks?...` |
| `DB_USERNAME` | Database username | `root` |
| `DB_PASSWORD` | Database password | `root` |

Example:

```bash
export DB_URL='jdbc:mysql://127.0.0.1:3306/agentworks?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true'
export DB_USERNAME='root'
export DB_PASSWORD='your-password'
```

### 4. Run the application

```bash
mvn spring-boot:run
```

Or build the JAR first:

```bash
mvn package
java -jar target/agentworks-0.0.1-SNAPSHOT.jar
```

### 5. Open API documentation

- Knife4j: `http://localhost:8080/doc.html`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`

## Running Tests

```bash
# Run the full suite
mvn test

# Run one integration test class
mvn -Dtest=ApiIntegrationTest test

# Run one test method
mvn -Dtest=ApiIntegrationTest#should_create_project_via_api test
```

Tests use H2 in MySQL compatibility mode and bootstrap the schema from `src/test/resources/schema.sql`.

## API Snapshot

| Method | Path | Description |
| --- | --- | --- |
| `POST` | `/api/projects` | Create a project |
| `GET` | `/api/projects?userId={id}` | Query a user's project list |
| `POST` | `/api/agents` | Add an agent member under a project |

## Deployment

### Current deployment mode

This repository currently ships as a standard Spring Boot JAR application:

```bash
mvn package
java -jar target/agentworks-0.0.1-SNAPSHOT.jar
```

### Docker

Containerization is not committed yet. Add a `Dockerfile` around the packaged JAR before deploying the service as a container.

### Cloud platforms

Cloud deployment manifests are not part of the repository yet. If you add them, keep the README aligned with the actual deployment path instead of documenting speculative infrastructure.

## Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feat/your-change`
3. Make your changes and run the relevant Maven tests
4. Push your branch and open a Pull Request

## Developed By

Maintained by Herb.

## Acknowledgements

- [Spring Boot](https://spring.io/projects/spring-boot)
- [MyBatis-Plus](https://baomidou.com/)
- [Knife4j](https://doc.xiaominfo.com/)
- [H2 Database](https://www.h2database.com/)

If this repository helps your work, consider giving it a star.
