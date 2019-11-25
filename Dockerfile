FROM openjdk:8-jre-slim

VOLUME /tmp

EXPOSE 8087

ARG JAR_FILE=$PWD/build/libs/alerts-service-1.0-SNAPSHOT.jar

ADD ${JAR_FILE} alerts-service.jar

ENTRYPOINT ["java","-Dserver.port=8087","-Djava.security.egd=file:/dev/./urandom","-jar","/alerts-service.jar"]