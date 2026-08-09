# Code Review Rules

1. PR title: short, imperative, includes scope (e.g., feat(product): add product entity).
2. Small PRs: prefer < 400 lines changed when possible.
3. Include tests: unit + integration for feature changes; BDD scenarios for business flows.
4. DDD alignment: changes must map to the bounded context; avoid cross-service model leakage.
5. Security checks: no secrets, validate inputs, use parameterized queries.
6. Performance: time complexity and DB queries review required for data-critical paths.
7. CI must pass: linters, tests, and BDD feature runs.
8. Reviewers: at least two approvers, one from domain owners and one infra/ops reviewer for infra changes.
9. Document breaking changes in PR body and update docs/.
10. Follow commit message guidelines and include tests verifying behavior.
