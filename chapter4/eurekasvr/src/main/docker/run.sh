#!/bin/sh
echo "********************************************************"
echo "Starting the Eureka Server"
echo "********************************************************"
java -D -jar /usr/local/eurekaserver/@project.build.finalName@.jar
