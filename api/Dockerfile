FROM openjdk:11
MAINTAINER Pedro Escaleira

COPY ./target/demo-0.0.1.jar /opt/spring-cloud/lib/

EXPOSE 8888

# ENTRYPOINT ["/usr/bin/java"]
CMD ["java", "-jar", "/opt/spring-cloud/lib/demo-0.0.1.jar"]
