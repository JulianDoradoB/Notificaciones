📬 Notifications Microservice
This microservice is a core component of a modular clinical management system. Its primary function is to manage and dispatch notifications for critical events such as payment confirmations and medical appointment reminders.

🛠️ Technologies Used
This project leverages a modern tech stack to ensure robustness, scalability, and ease of deployment:

Java 22: The latest LTS version of Java for robust backend development.
Spring Boot: Simplifies the development of production-ready, stand-alone Spring applications.
Maven: Dependency management and build automation tool.
Feign Client: A declarative web service client for seamless communication with other microservices.
Docker: For containerization, ensuring consistent environments across development and deployment.
Railway: A modern platform for effortless application deployment.

🚀 Getting Started
Follow these instructions to get your Notifications Microservice up and running.

🔧 Prerequisites
Before you begin, ensure you have the following installed:

Java 22
Maven
Docker (Optional, if deploying locally without containers)
An account on Railway.app (for cloud deployment)
▶️ Local Execution
To run the microservice directly on your local machine:

Build the project:
Bash

./mvnw clean install -DskipTests
Run the application:
Bash

java -jar target/*.jar
The application will start on http://localhost:8080.

🐳 Docker
A Dockerfile is included for containerizing the application, providing a consistent and isolated environment.

Dockerfile:

Dockerfile

FROM eclipse-temurin:22-jdk AS build

WORKDIR /app
COPY . .
RUN chmod +x ./mvnw && ./mvnw clean package -DskipTests

FROM eclipse-temurin:22-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
Building and Running with Docker:

Build the Docker image:
Bash

docker build -t notificaciones-app .
Run the Docker container:
Bash

docker run -p 8080:8080 notificaciones-app
The application will be accessible at http://localhost:8080.

☁️ Deployment on Railway
Railway offers a streamlined deployment process for your microservice:

Upload your project to GitHub: Ensure your project is pushed to a public or private GitHub repository.
Navigate to Railway.app: Log in to your account.
Create a New Project: Select the option to "Deploy from GitHub Repo."
Connect your repository: Railway will automatically detect the Dockerfile in your repository.
Verify Project Structure: Crucially, ensure that the Dockerfile and pom.xml are located directly in the root of your GitHub repository (e.g., your-repo/Dockerfile, your-repo/pom.xml), not within a subfolder.

📬 Expected Endpoints
This microservice primarily acts as a consumer of events from other services like "Pagos" (Payments) and "Citas" (Appointments). It then processes and dispatches relevant notifications.

The specific endpoints for this service are defined within the controller package, but here are common examples:

POST /notificaciones: Sends a new notification.
GET /notificaciones: Retrieves a list of all notifications.
GET /notificaciones/{id}: Fetches details for a specific notification by its ID.

🔗 Integration with Other Microservices
This service is designed to integrate seamlessly with other components of the clinical management system, specifically:

Microservicio de Pagos (Payments Microservice)
Microservicio de Citas (Appointments Microservice)
These integrations are facilitated using FeignClient, with client interfaces defined in the infrastructure.client package.

❓ Common Issues & Troubleshooting
Encountering problems? Here are solutions to frequently faced issues:

"Application failed to respond" on Railway:
Project Root: Verify that your project's main content (including Dockerfile and pom.xml) is at the root of your GitHub repository. Railway does not support subdirectories like Notificaciones/Notificaciones/.
Java 22 Configuration: Double-check that your Dockerfile and Railway environment variables are correctly configured for Java 22.
"Could not determine how to build":
This usually indicates that Railway cannot find the necessary build files (like pom.xml or Dockerfile) in the expected location. Move all relevant project content to the root directory of your repository.