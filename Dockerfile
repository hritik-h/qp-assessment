
FROM openjdk:21-jdk-slim

ARG JAR_FILE=target/booking-0.0.2-SNAPSHOT.jar

WORKDIR /app

COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
