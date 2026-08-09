# SampleAI E-commerce Platform

A sample e-commerce microservices platform demonstrating Domain-Driven Design (DDD), Behavior-Driven Development (BDD), Test-Driven Development (TDD), and 12-factor principles. Built with Spring Boot microservices (Java) and a React frontend.

Contents:
- services/: product, order, auth microservices (DB-per-service, Spring Boot)
- gateway/: Spring Cloud Gateway with rate-limiting and routing
- infra/: k8s manifests, Istio integration, Helm charts and third-party chart references
- frontend/: React app (Vite + TypeScript skeleton)
- docs/: architecture, development guides (DDD/BDD/TDD, 12-factor)

Recent additions:
- JWT-based authentication (Auth service) and JWT filters that populate SecurityContext with username and roles
- Product & Order services protected by role-based checks (ROLE_USER required for creating resources)
- Spring Cloud Gateway configured with RequestRateLimiter (Redis) and integration tests
- Kafka producer (Order)>consumer (Product) skeletons for domain events (topic: orders.created)
- Helm charts per-service and an umbrella chart; CI workflow to lint and template charts
- Istio DestinationRules/VirtualServices for circuit-breaking and rate-limiting placeholders

Quick start (local development):
1. Build services: mvn -f services/product-service/ test (repeat for order, auth, gateway).
2. Start services locally (ports: product 8081, order 8082, auth 8083, gateway 8080).
3. Start frontend: cd frontend/react-app && npm install && npm run dev.
4. For gateway rate limits, run Redis (or helm install bitnami/redis). For messaging, run Kafka (or use a managed Kafka).

CI & Deployment:
- GitHub Actions workflow (/.github/workflows/ci-helm-tests.yml) runs Helm lint/template and service unit tests.
- Helm charts are under infra/helm. The umbrella chart (infra/helm/ecommerce-chart) references per-service charts and recommended third-party charts for Redis/Kafka.

Notes & next steps:
- Secrets: use a secret manager (Vault/Openshift Secrets). Do not store secrets in repo.
- Replace placeholder Kafka/Redis manifests with production-grade Helm charts or use managed services.
- Rotate JWT keys and add automated secret rotation.
- Add end-to-end BDD scenarios in CI and expand operator/Helm integration for OpenShift.

See docs/ for detailed developer guides, BDD feature locations, and code review rules in CODE_REVIEW_RULES.md.
