FROM openjdk:17-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/DentalManagement-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot app runs on (change if needed)
EXPOSE 8080

# Command to run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
