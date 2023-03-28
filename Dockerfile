FROM openjdk:19-jdk
MAINTAINER MichaelMena
COPY portfolio-backend.jar portfolio-backend.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/portfolio-backend.jar"]