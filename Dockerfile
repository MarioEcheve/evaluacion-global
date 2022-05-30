FROM openjdk:8-jre-alpine
LABEL maintainer="marioandreseche@gmail.com"
RUN apk add --no-cache openjdk8
COPY target/ejercicio-0.0.1-SNAPSHOT.jar /opt/lib/evaluacion-global.jar
ENTRYPOINT ["/usr/bin/java"]
CMD ["-jar", "/opt/lib/evaluacion-global.jar"]
EXPOSE 8080