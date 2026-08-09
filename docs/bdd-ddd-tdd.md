# BDD / DDD / TDD Guidelines

- DDD: define bounded contexts (product, order, customer, auth). Each service owns its model, repository, and APIs.
- BDD: author feature files in /services/<domain>/features using Gherkin (Given/When/Then). CI will run these via cucumber/junit.
- TDD: write unit tests with JUnit + Mockito for Java services. Maintain high unit-test coverage for domain logic.

Example workflow:
1. Write failing BDD scenario and unit tests.
2. Implement domain logic using TDD.
3. Run integration tests and BDD scenarios.
4. Submit PR with tests and feature files.
