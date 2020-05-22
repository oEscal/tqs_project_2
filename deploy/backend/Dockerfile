FROM openjdk:11
MAINTAINER Pedro Escaleira

COPY ./target/demo-0.0.1-SNAPSHOT.jar /opt/spring-cloud/lib/

EXPOSE 8888

CMD ["java", "-jar", "/opt/spring-cloud/lib/demo-0.0.1-SNAPSHOT.jar"]
