#!/bin/bash

./gradlew clean assemble
docker build -t sample-kafka-client .
docker-compose -f ./docker-compose-bitnami.yml up &
./gradlew test -i