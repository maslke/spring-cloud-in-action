FROM openjdk:8-jdk-alpine
RUN apk update && apk upgrade
RUN mkdir -p /usr/local/eurekaserver
ADD eurekasvr-0.0.1-SNAPSHOT.jar /usr/local/eurekaserver/
CMD java -jar usr/local/eurekaserver/eurekasvr-0.0.1-SNAPSHOT.jar