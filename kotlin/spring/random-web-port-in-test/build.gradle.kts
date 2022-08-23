plugins {
    `java-library`
    kotlin("jvm") version "1.7.10"
    id("org.springframework.boot") version "2.7.3"
}
apply(plugin = "io.spring.dependency-management")

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}