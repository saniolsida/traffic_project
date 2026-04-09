package com.example.oliveold.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PurchaseProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public PurchaseProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
}

