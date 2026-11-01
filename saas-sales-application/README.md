# SaaS Sales Application — GitHub + DevOps Demo

A Spring Boot REST API for sales lead management with Docker, Jenkins and Kubernetes deployment examples.

## Requirements
- Java 17
- Maven 3.9+
- Docker (for container build)
- kubectl and a configured Kubernetes cluster (optional)

## Run locally
```bash
mvn clean test package
mvn spring-boot:run
```
Open http://localhost:8080.

## API endpoints
| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/api/leads` | List all leads |
| GET | `/api/leads/1` | Get a lead |
| POST | `/api/leads` | Create a lead |
| DELETE | `/api/leads/3` | Delete a lead |
| GET | `/actuator/health` | Health check |

Example:
```bash
curl -X POST http://localhost:8080/api/leads -H 'Content-Type: application/json' -d '{"name":"Neha Joshi","email":"neha@example.com","company":"Example Co","status":"NEW"}'
```

## Docker
```bash
docker build -t saas-sales-application:local .
docker run --rm -p 8080:8080 saas-sales-application:local
```
Or run `docker compose up --build`.

## Kubernetes
Replace `REPLACE_WITH_ECR_IMAGE_URI` in `k8s/deployment.yaml`, then:
```bash
kubectl apply -f k8s/namespace.yaml
kubectl apply -f k8s/
kubectl get pods -n sales
kubectl get svc -n sales
```

## Jenkins / AWS
The Jenkinsfile is a template. Configure Jenkins with Java/Maven, Docker, AWS CLI, kubectl, AWS credentials and the Pipeline: AWS Steps plugin (`withAWS`). Grant required ECR/EKS permissions. Replace account and cluster placeholders before running. Ensure the Kubernetes deployment exists before using `kubectl set image`.

## Architecture
GitHub → Jenkins → Maven build/test → Docker image → Amazon ECR → Amazon EKS → Kubernetes Service → optional ALB Ingress.

## Demo limitations
- Lead records are stored in memory and reset when the application restarts.
- This is a learning project, not production-ready. Add a database, authentication, authorization, robust validation, secrets management, monitoring and security hardening before production use.
- Terraform folder is a placeholder and creates no infrastructure.
