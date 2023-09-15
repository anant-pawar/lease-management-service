FROM gradle:8.3.0-jdk17-alpine AS build
WORKDIR /app
COPY . .
RUN gradle clean build -x test

FROM eclipse-temurin:17-jdk-alpine
COPY --from=build /app/build/libs/lease-management-service-*.jar /app.jar
EXPOSE 8080
CMD ["java", "-Dspring.profiles.active=dev", "-jar", "/app.jar"]