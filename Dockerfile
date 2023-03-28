FROM openjdk:20-jdk
MAINTAINER MichaelMena
COPY target/portfolio-backend.jar portfolio-backend.jar
ENTRYPOINT ["java","-jar","/portfolio-backend.jar"]
