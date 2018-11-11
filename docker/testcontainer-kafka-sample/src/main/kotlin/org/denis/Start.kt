package org.denis

import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.Network

fun main(args: Array<String>) {

    val network = Network.newNetwork()

    val zookeeper = GenericContainer<Nothing>("bitnami/zookeeper:3.4.12").apply {
        addExposedPort(2181)
        withNetwork(network)
        withNetworkAliases("zookeeper")
        addEnv("ALLOW_ANONYMOUS_LOGIN", "yes")
    }
    zookeeper.start()

    val kafka = GenericContainer<Nothing>("bitnami/kafka:2.0.0").apply {
        withNetwork(network)
        addExposedPort(9092)
        withNetworkAliases("kafka")
        addEnv("KAFKA_ZOOKEEPER_CONNECT", "zookeeper:2181")
        addEnv("ALLOW_PLAINTEXT_LISTENER", "yes")
        addEnv("KAFKA_LISTENERS", "PLAINTEXT://:9092")
        addEnv("KAFKA_ADVERTISED_LISTENERS", "PLAINTEXT://kafka:9092")
    }
    kafka.start()
}