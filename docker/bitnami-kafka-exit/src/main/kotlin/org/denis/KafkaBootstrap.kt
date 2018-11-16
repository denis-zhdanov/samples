package org.denis

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.ByteArrayDeserializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer
import org.springframework.kafka.listener.MessageListener
import org.springframework.kafka.listener.config.ContainerProperties
import org.springframework.stereotype.Component

@Component
class KafkaBootstrap(
        @param:Value("\${KAFKA_HOST:127.0.0.1}") private val host: String,
        @param:Value("\${KAFKA_PORT:9092}") private val port: Int
) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        val topicName = "EXTERNAL_ACCOUNT_CHECK_REQUEST"

        val consumerConfig = mapOf(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to "http://$host:$port",
                ConsumerConfig.GROUP_ID_CONFIG to "$topicName-consumer-group"
        )

        val consumerFactory = DefaultKafkaConsumerFactory(
                consumerConfig,
                StringDeserializer(),
                ByteArrayDeserializer()
        )

        val containerProperties = ContainerProperties(topicName)
        containerProperties.messageListener = MessageListener<String, ByteArray> {
            println("Received a message")
        }

        println("Starting a consumer")
        ConcurrentMessageListenerContainer(consumerFactory, containerProperties).start()
        println("Started a consumer")
    }
}