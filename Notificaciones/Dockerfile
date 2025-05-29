# Usa Java 22 para la etapa de construcción
FROM eclipse-temurin:22-jdk AS build

# Establece directorio de trabajo
WORKDIR /app

# Copia archivos del proyecto
COPY . .

# Da permisos y compila el proyecto sin tests
RUN chmod +x ./mvnw && ./mvnw clean package -DskipTests

# Imagen final para ejecución con Java 22
FROM eclipse-temurin:22-jdk

WORKDIR /app

# Copia solo el JAR ya construido desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Expone el puerto por defecto de Spring Boot
EXPOSE 8080

# Comando de ejecución
ENTRYPOINT ["java", "-jar", "app.jar"]
