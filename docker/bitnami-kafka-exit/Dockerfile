FROM openjdk:8-alpine3.8

COPY ./build/libs /opt/myapp/lib/

EXPOSE 8080

WORKDIR /opt/myapp

ENV APPLICATION_NAME myapp
ENV KAFKA_HOST 127.0.0.1
ENV KAFKA_PORT 9092
ENV JVM_OPTIONS ""

CMD ["/bin/sh", "-c", "java $JVM_OPTIONS -jar /opt/myapp/lib/bitnami-kafka-exit.jar"]