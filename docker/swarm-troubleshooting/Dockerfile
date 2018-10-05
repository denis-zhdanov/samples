FROM openjdk:8-alpine3.8

COPY ./build/libs /opt/myapp/lib/

EXPOSE 8090

WORKDIR /opt/myapp

CMD ["java", "-jar", "/opt/myapp/lib/myapp.jar"]