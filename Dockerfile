FROM openjdk:19.0.1
MAINTAINER MichaelMena
COPY portfolio-backend.jar portfolio-backend.jar
ENTRYPOINT ["java","-jar","portfolio-backend.jar"]