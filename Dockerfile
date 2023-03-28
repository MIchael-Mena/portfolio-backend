EXPOSE 8080
FROM openjdk:19-jdk
MAINTAINER MichaelMena
COPY portfolio-backend.jar portfolio-backend.jar
ENTRYPOINT ["java","-jar","/portfolio-backend.jar"]