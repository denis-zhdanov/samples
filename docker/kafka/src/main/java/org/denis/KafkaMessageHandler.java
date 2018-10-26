package org.denis;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageHandler {

    private final KafkaTemplate<String, String> template;

    public KafkaMessageHandler(KafkaTemplate<String, String> template) {
        this.template = template;
    }

    @KafkaListener(topics = "my-request")
    public void onMessage(String content) {
        System.out.printf("Received a message '%s'%n", content);
        template.send("my-response", String.format("echo-%s", content));
    }
}
