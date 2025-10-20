# auth-service/Dockerfile
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /app

COPY pom.xml .
COPY catalog-service   ./catalog-service
COPY user-service      ./user-service
COPY auth-service      ./auth-service
COPY order-service     ./order-service
COPY gateway-service   ./gateway-service
COPY discovery-service ./discovery-service

RUN mvn clean package -pl auth-service -am -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/auth-service/target/auth-service-*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
