# --- Build stage ---
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

COPY src ./src
RUN ./mvnw clean package -DskipTests

# --- Run stage ---
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/task-manager-web-1.0.0.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]