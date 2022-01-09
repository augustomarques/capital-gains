FROM maven:3.8.3-openjdk-17-slim AS build
WORKDIR /build

ADD . /build
RUN mvn clean package -DskipTests --batch-mode

FROM eclipse-temurin:17.0.1_12-jre-focal AS release
COPY --from=build /build/target/capital-gains-1.0-SNAPSHOT-jar-with-dependencies.jar /app.jar
ENTRYPOINT [ "java", "-jar", "/app.jar" ]