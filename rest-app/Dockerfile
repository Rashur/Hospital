FROM openjdk:11.0.13
WORKDIR usr/src/app
COPY target/docker-rest-app.jar rest-app.jar
ENTRYPOINT ["java", "-jar", "rest-app.jar"]