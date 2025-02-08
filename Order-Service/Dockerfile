# Use a base image with Java and a minimal Linux distribution
FROM openjdk:17-jdk-slim-buster

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from your build directory into the container
COPY target/Order-Service-0.0.1-SNAPSHOT.jar .

# Expose the port your Spring Boot application is listening on (default is 8080)
# Http
EXPOSE 8080
# Https
# EXPOSE 8443

# Define env variables (e.g Spring Profile)
# ENV SPRING_PROFILES_ACTIVE=production

# Command to run your application
CMD ["java", "-jar", "Order-Service-0.0.1-SNAPSHOT.jar"]