#!/bin/bash

./gradlew clean bootJar
docker build -t docker-swarm-troobleshoot-service .