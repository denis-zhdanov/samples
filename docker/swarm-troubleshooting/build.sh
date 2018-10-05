#!/bin/bash

./gradlew clean bootJar
docker build -t docker-swarm-troobleshoot-service .

docker swarm init
docker stack deploy -c docker-compose.yml myapp