FROM openjdk:21-jdk-slim
ARG JAR_FILE=target/fastdelivery-0.0.1.jar
COPY ${JAR_FILE} fastdelivery.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar", "fastdelivery.jar"]