📬 Microservicio de Notificaciones
Este microservicio es un componente central de un sistema modular de gestión clínica. Su función principal es gestionar y enviar notificaciones para eventos críticos como confirmaciones de pago y recordatorios de citas médicas.

🛠️ Tecnologías Utilizadas
Este proyecto utiliza un stack tecnológico moderno para garantizar robustez, escalabilidad y facilidad de despliegue:

Java 22: La versión LTS más reciente de Java para un desarrollo backend robusto.

Spring Boot: Facilita el desarrollo de aplicaciones Spring listas para producción.

Maven: Herramienta para gestión de dependencias y automatización de builds.

Feign Client: Cliente web declarativo para comunicación fluida con otros microservicios.

Docker: Para la contenerización, garantizando entornos consistentes en desarrollo y despliegue.

Railway: Plataforma moderna para desplegar aplicaciones sin complicaciones.

🚀 Primeros Pasos
Sigue estas instrucciones para poner en marcha el Microservicio de Notificaciones.

🔧 Requisitos Previos
Antes de comenzar, asegúrate de tener instalados:

Java 22

Maven

Docker (opcional, si vas a desplegar localmente sin contenedores)

Una cuenta en Railway.app (para despliegue en la nube)

▶️ Ejecución Local
Para ejecutar el microservicio directamente en tu máquina local:

Compila el proyecto:

bash
Copiar
Editar
./mvnw clean install -DskipTests
Ejecuta la aplicación:

bash
Copiar
Editar
java -jar target/*.jar
La aplicación estará disponible en: http://localhost:8080

🐳 Uso de Docker
Se incluye un Dockerfile para contenerizar la aplicación, proporcionando un entorno aislado y consistente.

Dockerfile:

dockerfile
Copiar
Editar
FROM eclipse-temurin:22-jdk AS build
WORKDIR /app
COPY . .
RUN chmod +x ./mvnw && ./mvnw clean package -DskipTests

FROM eclipse-temurin:22-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
Construcción y Ejecución con Docker:

bash
Copiar
Editar
docker build -t notificaciones-app .
docker run -p 8080:8080 notificaciones-app
La aplicación estará disponible en: http://localhost:8080

☁️ Despliegue en Railway
Railway ofrece un proceso simplificado para desplegar tu microservicio:

Sube tu proyecto a GitHub: Asegúrate de que el repositorio sea público o privado.

Accede a Railway.app e inicia sesión.

Crea un nuevo proyecto seleccionando la opción "Deploy from GitHub Repo".

Conecta tu repositorio: Railway detectará automáticamente el Dockerfile.

Verifica la estructura del proyecto:

Asegúrate de que tanto Dockerfile como pom.xml estén en la raíz del repositorio:

✅ tu-repo/Dockerfile

✅ tu-repo/pom.xml

❌ tu-repo/Notificaciones/Dockerfile

📬 Endpoints Esperados
Este microservicio actúa principalmente como consumidor de eventos de otros servicios como Pagos y Citas, y luego procesa y envía las notificaciones correspondientes.

Ejemplos comunes de endpoints:

POST /notificaciones: Envía una nueva notificación.

GET /notificaciones: Recupera una lista de todas las notificaciones.

GET /notificaciones/{id}: Obtiene detalles de una notificación específica por su ID.

🔗 Integración con Otros Microservicios
Este servicio se integra fácilmente con otros componentes del sistema de gestión clínica, específicamente con:

Microservicio de Pagos

Microservicio de Citas

Estas integraciones se realizan utilizando FeignClient, con interfaces cliente definidas en el paquete infrastructure.client.

❓ Problemas Comunes y Soluciones
"Application failed to respond" en Railway:

Estructura del Proyecto: Verifica que Dockerfile y pom.xml estén en la raíz del repositorio.

Configuración de Java 22: Asegúrate de que el Dockerfile y las variables de entorno estén configuradas correctamente.

"Could not determine how to build":

Railway no puede encontrar los archivos necesarios (pom.xml, Dockerfile) en la ubicación esperada.
➤ Solución: Mueve todos los archivos del proyecto directamente a la raíz del repositorio.