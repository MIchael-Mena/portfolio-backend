FROM openjdk:19-oracle
MAINTAINER MichaelMena
COPY portfolio-backend.jar portfolio-backend.jar
ENTRYPOINT ["java","-jar","portfolio-backend.jar"]