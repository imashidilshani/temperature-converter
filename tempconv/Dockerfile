
# 1 Stage: Build Stage
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

RUN find . -name "*.jar" -o -name "*.war"

# 2 Stage: Runtime Stage
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target-backend/*.*ar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]