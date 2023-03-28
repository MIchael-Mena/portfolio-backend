FROM openjdk:18-jdk
MAINTAINER MichaelMena
COPY portfolio-backend.jar portfolio-backend.jar
ENTRYPOINT ["java","-jar","portfolio-backend.jar"]