# Copilot instructions

## Build, test, and lint commands

- Full test suite: `mvn test`
- Build artifact: `mvn package`
- Run one test class: `mvn -Dtest=ApiIntegrationTest test`
- Run one test method: `mvn -Dtest=ApiIntegrationTest#should_create_project_via_api test`
- There is no dedicated lint or formatting task configured in `pom.xml` yet.

## High-level architecture

- This repository is a Spring Boot 3.2 monolith with a conventional layered flow: `controller -> service -> mapper -> database`.
- `com.agentworks.AgentWorksApplication` uses `@MapperScan("com.agentworks.mapper")`, so MyBatis-Plus mapper interfaces are the persistence boundary and there are no XML mappers at this stage.
- Controllers under `com.agentworks.controller` expose `/api/**` endpoints, validate request DTOs from `com.agentworks.controller.request`, and always return `com.agentworks.common.Result<T>`.
- Service implementations in `com.agentworks.service.impl` extend MyBatis-Plus `ServiceImpl`. They are the main place for business defaults and cross-entity checks, such as defaulting project status to `INIT`, defaulting agent status to `IDLE`, and rejecting agent creation when the parent project does not exist.
- Entities in `com.agentworks.domain.entity` map directly to the `aw_*` tables. All entities inherit audit fields and logical deletion from `BaseEntity`.
- Runtime persistence targets MySQL from `src/main/resources/application.yml`, but tests run against H2 in MySQL mode using `src/test/resources/application.yml` and `src/test/resources/schema.sql`. If you change entity persistence behavior, keep the H2 schema in sync.

## Key conventions

- Database naming is snake_case and Java naming is camelCase. MyBatis-Plus underscore-to-camel mapping is enabled globally, so new entity fields should follow the existing Java-side camelCase convention.
- Status and type columns are modeled as Java enums in `com.agentworks.common.enums` and persisted as strings through the configured enum type handler. Do not introduce ad-hoc string literals for domain states.
- Audit fields (`createTime`, `updateTime`, `createBy`, `updateBy`) and logical deletion (`isDeleted`) belong in `BaseEntity`; they are populated by `com.agentworks.config.MybatisMetaObjectHandler`.
- JSON-like database columns are intentionally stored as raw `String` values for now (`techStack`, `modelConfig`). Do not introduce custom JSON type handlers unless the repository explicitly moves in that direction.
- Request validation messages are written in Chinese and surfaced directly through `GlobalExceptionHandler`, which returns the first validation error inside `Result.failure(...)`.
- Controllers currently map request DTOs to entities manually. There is no assembler or MapStruct-based conversion layer yet, even though MapStruct is already in the build.
- The current project list endpoint is not authentication-aware; it takes `userId` as a request parameter. Do not assume a login context or security principal exists yet.
- Integration tests prefer real Spring Boot wiring over mocked mappers. `ApiIntegrationTest`, `ProjectServiceIntegrationTest`, and `AgentServiceIntegrationTest` use `@SpringBootTest`, `@Transactional`, and the H2 schema to exercise actual persistence behavior.
