#FROM openjdk:8-jdk-alpine
FROM openjdk:11-jre

VOLUME /tmp

ARG JAR_FILE=build/libs/Whale-telegram-pars-1.0-all.jar

ADD ${JAR_FILE} telegram-client.jar

ENTRYPOINT ["java","-jar","/telegram-client.jar"]

#ENTRYPOINT java -jar telegram-client.jar # "shell" format