FROM amazoncorretto:17.0.5
MAINTAINER MichaelMena
COPY portfolio-backend.jar portfolio-backend.jar
ENTRYPOINT ["java","-jar","/portfolio-backend.jar]