plugins {
    `java-library`
    kotlin("jvm") version "1.8.20"
    id("org.springframework.boot") version "3.0.6"
}
apply(plugin = "io.spring.dependency-management")

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.20")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}