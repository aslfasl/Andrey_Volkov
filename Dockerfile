FROM openjdk:11-jre-slim

VOLUME /tmp

ADD /target/124-12-Volkov-0.0.1-SNAPSHOT.jar 124-12-Volkov-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/124-12-Volkov-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080