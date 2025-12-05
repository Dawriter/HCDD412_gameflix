# ---------- 1) Build stage ----------
FROM maven:3.9.9-eclipse-temurin-21 AS build

# Work directory inside the container
WORKDIR /app

# Copy pom.xml and download dependencies (for better cache)
COPY pom.xml .
RUN mvn -q dependency:go-offline

# Copy source code
COPY src ./src

# Build the Spring Boot fat jar (skip tests for speed)
RUN mvn -q clean package -DskipTests


# ---------- 2) Runtime stage ----------
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose Spring Boot's default port
EXPOSE 8080

# Start the app
ENTRYPOINT ["java", "-jar", "app.jar"]