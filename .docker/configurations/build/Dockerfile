FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /usr/src/app

COPY ./pom.xml ./pom.xml
COPY ./src ./src

RUN --mount=type=cache,target=/root/.m2 mvn package -DskipTests

FROM eclipse-temurin:21-alpine

WORKDIR /usr/src/app

ENV SERVER_PORT=80
ENV DB_SHOW_SQL=false
ENV DOCKERIZE_VERSION v0.6.1

COPY --from=builder /usr/src/app/target/*.jar ./api.jar

RUN apk add --no-cache openssl

RUN wget https://github.com/jwilder/dockerize/releases/download/$DOCKERIZE_VERSION/dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && tar -C /usr/local/bin -xzvf dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && rm dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz

EXPOSE ${SERVER_PORT}

ENTRYPOINT [ "java", "-jar", "api.jar" ]