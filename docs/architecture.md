# Architecture Overview

This project follows microservice architecture divided by DDD domains. Key aspects:

- Each service owns its database (DB-per-service).
- API Gateway routes requests, handles auth checks and rate limiting.
- Istio (or service mesh) for observability, mTLS, and circuit breakers.
- Circuit breaker pattern and rate-limiting configured at mesh and gateway.
- Services communicate via REST and events (async) where appropriate.

See docs/development.md for coding standards.