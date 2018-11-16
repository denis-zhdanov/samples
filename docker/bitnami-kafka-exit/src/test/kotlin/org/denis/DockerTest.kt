package org.denis

import org.junit.Test
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.Network

class DockerTest {

    @Test
    fun test() {
        val network = Network.NetworkImpl.builder().build()

        val zookeeper = GenericContainer<Nothing>("bitnami/zookeeper:3.4.12").apply {
            addExposedPort(2181)
            withNetwork(network)
            withNetworkAliases("zookeeper")
            addEnv("ALLOW_ANONYMOUS_LOGIN", "yes")
        }

        val kafka = GenericContainer<Nothing>("bitnami/kafka:2.0.0").apply {
            withNetwork(network)
            addExposedPort(9092)
            withNetworkAliases("kafka")
            addEnv("KAFKA_ZOOKEEPER_CONNECT", "zookeeper:2181")
            addEnv("ALLOW_PLAINTEXT_LISTENER", "yes")
            addEnv("KAFKA_LISTENERS", "PLAINTEXT://:9092")
            addEnv("KAFKA_ADVERTISED_LISTENERS", "PLAINTEXT://kafka:9092")
        }

        val application = GenericContainer<Nothing>("myapp").apply {
            withNetwork(network)
            addEnv("KAFKA_HOST", "kafka")
//            addExposedPort(5005)
//            addEnv("JVM_OPTIONS", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005")
        }

        zookeeper.start()
        kafka.start()
        application.start()

        Thread.currentThread().join()
    }
}