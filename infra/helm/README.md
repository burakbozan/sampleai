Helm charts for deploying the e-commerce platform.

Charts:
- product-service/
- order-service/
- auth-service/
- api-gateway/
- ecommerce-chart/ (umbrella that references the above charts as dependencies)

Use 'helm install -f values.yaml' per-chart or the umbrella to deploy to Kubernetes.
