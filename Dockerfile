FROM openjdk:21
LABEL maintainer="notedeagle"
WORKDIR /app
COPY ./.mvn .mvn
COPY ./mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw dependency:go-offline

COPY ./src ./src

CMD ["./mvnw", "spring-boot:run"]