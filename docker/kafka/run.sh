#!/bin/bash

./gradlew clean assemble
docker build -t sample-kafka-client .
docker-compose up
./gradlew test -i