FROM openjdk:17-jdk-alpine

RUN apt-get update && apt-get install -y maven

RUN mvn clean package -DskipTests

WORKDIR /app

COPY --from=build /app/target/store-management-1.0.0.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
