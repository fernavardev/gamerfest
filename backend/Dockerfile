# Build
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -B -DskipTests package

# Runtime
FROM eclipse-temurin:21-jre
WORKDIR /app

RUN groupadd --system app && useradd --system --gid app --no-create-home app

COPY --from=build --chown=app:app /app/target/*.jar app.jar

USER app

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
