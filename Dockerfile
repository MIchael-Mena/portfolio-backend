FROM openjdk:19-jdk-alpine
MAINTAINER MichaelMena
COPY portfolio-backend.jar portfolio-backend.jar
ENTRYPOINT ["java","-jar","portfolio-backend.jar"]