FROM openjdk:16-alpine
LABEL maintainer="daviMustafa"

# image layer
VOLUME /developments
WORKDIR /developments

EXPOSE 8081

ADD ./target/interview-calendar-api*jar /developments/interview-calendar-api.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/developments/interview-calendar-api.jar"]