Helm charts for deploying the e-commerce platform.

Charts:
- product-service/
- order-service/
- auth-service/
- api-gateway/
- ecommerce-chart/ (umbrella that references the above charts as dependencies)

Use 'helm install -f values.yaml' per-chart or the umbrella to deploy to Kubernetes.

Production-ready Redis & Kafka (recommended using Bitnami charts)

1) Add Helm repo and update:

   helm repo add bitnami https://charts.bitnami.com/bitnami
   helm repo update

2) Recommended Redis install (with password stored in a secret):

   kubectl create secret generic redis-password --from-literal=redis-password="$(openssl rand -base64 24)"
   helm install market-redis bitnami/redis \
     --set auth.existingSecret=redis-password \
     --set architecture=standalone \
     --set persistence.enabled=true \
     --set resources.requests.memory=256Mi --set resources.requests.cpu=100m

   Notes: For HA use clustered architecture; enable TLS/auth for production and configure persistence storageClass.

3) Recommended Kafka install (Bitnami) example:

   helm install market-kafka bitnami/kafka \
     --set replicaCount=3 \
     --set zookeeper.replicaCount=3 \
     --set persistence.enabled=true \
     --set resources.requests.memory=512Mi --set resources.requests.cpu=200m

   Notes: Bitnami's kafka chart can be tuned for auth (SASL/TLS), storage, and monitoring. Prefer managed Kafka for production.

4) Example umbrella install (render templates locally first):

   cd infra/helm/ecommerce-chart
   helm dependency update
   helm template market-store . --values values.yaml

   # To install and pass production secrets / image tags
   helm install market-store . \
     --set product-service.image.repository=market-store/product-service \
     --set product-service.image.tag=1.0.0 \
     --set auth.jwtSecret="<VAULT_VALUE_OR_SECRET>" \
     --set redis.auth.existingSecret=redis-password \
     --set kafka.replicaCount=3 --set kafka.zookeeper.replicaCount=3

Deprecated k8s placeholders

The manifests under infra/k8s (redis-deployment.yaml, kafka-deployment.yaml) are placeholders for local testing only and are deprecated. Use the Helm charts above or a managed service in production. See this file for example commands and recommended values.
