# E-commerce Microservices

Monorepo scaffold for an e-commerce platform using DDD, microservices, Spring Boot (Java) backend and React frontend.

Contents:
- services/: product, order, auth microservices (each DB-per-service, Spring Boot)
- gateway/: API Gateway (Spring Cloud Gateway)
- infra/: k8s/openshift manifests, Istio integration
- frontend/: React app
- docs/: architecture, development guides (DDD/BDD/TDD, 12-factor)

See docs/ for policies and development rules.
