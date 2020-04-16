FROM alpine:edge

MAINTAINER prafull.kulshrestha@gmail.com

RUN apk add --no-cache openjdk8

EXPOSE 8889

COPY . /tmp

RUN ls -la /tmp

COPY target/*.jar app/sd-example-api.jar


# Define working directory.
WORKDIR /app


CMD [ "java","-jar","sd-example-api.jar" ]
