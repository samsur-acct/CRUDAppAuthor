# Use ARM64-compatible Temurin JDK 17 image
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy the Spring Boot JAR (after building with Maven)
COPY target/CRUDApplication-0.0.1-SNAPSHOT.jar app.jar

# Expose Spring Boot port
EXPOSE 9090

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
