FROM openjdk:17-jdk-slim

COPY . .

RUN ./mvnw clean install -DskipTests

ENTRYPOINT ["java", "-jar", "target/backend-0.0.1-SNAPSHOT.jar"]