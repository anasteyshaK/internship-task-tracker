# Repository guidance

## Project

- This is a Java 17 Maven console application.
- Production sources live in `src/main/java/org/example` and tests belong in `src/test/java/org/example`.
- Keep the existing package structure (`model`, `repository`, `service`, and `exception`).

## Build and tests

- Run the full unit test suite with `mvn test`.
- Use JUnit Jupiter for tests. Prefer testing observable behavior through public APIs.
- Keep tests deterministic: use fixed dates and times instead of relying on the wall clock where possible.

## Design conventions

- Services coordinate repositories and enforce application-level validation.
- Repositories are in-memory implementations of their corresponding interfaces.
- Preserve the existing exception types when adding failure behavior.
- Avoid adding dependencies unless they support a clear project need.
