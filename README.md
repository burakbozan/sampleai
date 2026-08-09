# E-commerce Microservices

Monorepo scaffold for an e-commerce platform using DDD, microservices, Spring Boot (Java) backend and React frontend.

Contents:
- services/: product, order, auth microservices (each DB-per-service, Spring Boot)
- gateway/: API Gateway (Spring Cloud Gateway)
- infra/: k8s/openshift manifests, Istio integration, placeholders for Redis/Kafka
- frontend/: React app (Vite + TypeScript skeleton)
- docs/: architecture, development guides (DDD/BDD/TDD, 12-factor)

Quick start (local development):
1. Build Java services with Maven (each service folder) and run them locally (ports: 8081 product, 8082 order, 8083 auth, 8080 gateway).
2. Start frontend: cd frontend/react-app && npm install && npm run dev.
3. For rate-limiter tests, run a Redis instance and configure auth.jwt.secret env var for JWT.

Notes & next steps:
- Secrets: store JWT secrets and sensitive configs in a secret manager (Vault/Openshift Secrets). Do not commit secrets.
- Messaging: Kafka manifests are placeholders. Use managed Kafka or production-grade Helm charts and storage for production.
- Security: JWT utilities and filters are included; next implement SecurityContext principal population and role checks.
- Observability & Resilience: Istio DestinationRules and VirtualServices added; add circuit-breaker policies and health checks per service.
- CI/CD: add pipelines (build, test, BDD) and Helm charts for deployable manifests. Favor GitOps for production deployments.

See docs/ for detailed developer guides, BDD feature locations, and code review rules in CODE_REVIEW_RULES.md.
