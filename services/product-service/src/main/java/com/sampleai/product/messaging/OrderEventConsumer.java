package com.sampleai.product.messaging;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventConsumer {

    @KafkaListener(topics = "orders.created", groupId = "product-service")
    public void handleOrderCreated(String payload) {
        // TODO: parse payload and reserve inventory / call domain logic
        System.out.println("Received order.created event: " + payload);
    }
}
