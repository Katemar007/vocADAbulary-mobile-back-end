# --- Stage 1: Build the app ---
FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /app

# Copy all source code
COPY . .

# Build the app using Maven
RUN mvn clean package -DskipTests

# --- Stage 2: Run the app ---
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copy the built jar file from the previous stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the port your app runs on (change if needed)
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]