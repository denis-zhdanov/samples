Complete InfluxDB + Spring Boot setup.

* [docker-compose.yml](docker-compose.yml) - defines Docker configuration for InfluxDB and Grafana
* [application.yml](src/main/resources/application.yml) - configures InfluxDB for using by the application
* [Controller.kt](src/main/kotlin/org/denis/spring/micrometer/Controller.kt) - application's web controller which publishes stats about its calls
* [Client.kt](src/main/kotlin/org/denis/spring/micrometer/Client.kt) - client which calls target controller (to generate stats)