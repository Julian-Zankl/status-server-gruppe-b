FROM maven:3.9.6-eclipse-temurin-21 AS build

COPY src /home/app/src
COPY pom.xml /home/app

RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:21-slim
COPY --from=build /home/app/target/*.jar /usr/local/lib/app.jar

ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]