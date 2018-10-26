package org.denis;

import com.google.common.collect.ImmutableMap;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ApplicationTest {

    @Test
    public void externalDocker() {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(
                ImmutableMap.<String, Object>builder()
                        .put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:29092")
                        .put(ConsumerConfig.GROUP_ID_CONFIG, "test-group")
                        .put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
                        .build(),
                new StringDeserializer(),
                new StringDeserializer()
        );
        consumer.subscribe(Collections.singleton("my-response"));

        KafkaProducer<String, String> producer = new KafkaProducer<>(
                ImmutableMap.<String, Object>builder()
                        .put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:29092")
                        .put(ProducerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString())
                        .build(),
                new StringSerializer(),
                new StringSerializer()
        );

        System.out.println("About to send a kafka message");
        producer.send(new ProducerRecord<>("my-request", null, "hey"));

        ConsumerRecords<String, String> response = consumer.poll(3000);
        assertThat(response).isNotNull();
        assertThat(response.iterator().next().value()).isEqualTo("echo-hey");
    }

}
