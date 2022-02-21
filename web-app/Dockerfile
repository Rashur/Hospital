FROM openjdk:11.0.13
WORKDIR usr/src/app
COPY target/docker-web-app.jar web-app.jar
ENTRYPOINT ["java", "-jar", "web-app.jar"]