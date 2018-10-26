FROM openjdk:8-alpine3.8

COPY ./build/libs /opt/myapp/lib/

WORKDIR /opt/myapp

CMD ["java", "-jar", "/opt/myapp/lib/kafka-sample.jar"]