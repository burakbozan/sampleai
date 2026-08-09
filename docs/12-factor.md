# Twelve-Factor App Checklist

1. Codebase: one codebase tracked in git, many deploys.
2. Dependencies: explicit via build files (Maven/Gradle for backend; npm/yarn for frontend).
3. Config: use environment variables for runtime config.
4. Backing services: treat DB, message brokers as attached resources.
5. Build, release, run: separate stages in CI/CD.
6. Processes: stateless services; persist in backing stores.
7. Port binding for services.
8. Concurrency via process model.
9. Disposability: fast startup/shutdown.
10. Dev/prod parity.
11. Logs as event streams.
12. Admin processes via one-off process execution.
