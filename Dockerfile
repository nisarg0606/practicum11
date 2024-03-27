FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17
COPY --from=build /target/*.jar practicum11.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "practicum11.jar"]