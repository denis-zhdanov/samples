#!/bin/bash

./gradlew -x test build
docker build -t myapp .
./gradlew test &
watch docker ps -a