FROM openjdk:22-jdk-slim

WORKDIR /app

COPY target/student_management-0.0.1-SNAPSHOT.jar /app/testcicd.jar

EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "testcicd.jar"]