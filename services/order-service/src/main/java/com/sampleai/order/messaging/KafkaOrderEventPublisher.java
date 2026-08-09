package com.sampleai.order.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sampleai.order.domain.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaOrderEventPublisher implements OrderEventPublisher {
    private final KafkaTemplate<String, String> kafka;
    private final ObjectMapper mapper = new ObjectMapper();

    public KafkaOrderEventPublisher(KafkaTemplate<String, String> kafka) { this.kafka = kafka; }

    @Override
    public void publishOrderCreated(Order order) {
        try {
            String payload = mapper.writeValueAsString(order);
            kafka.send("orders.created", payload);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
