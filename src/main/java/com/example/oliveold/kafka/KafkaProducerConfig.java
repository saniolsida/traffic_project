package com.example.oliveold.kafka;

import com.example.oliveold.dto.PurchaseRequest;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    // 1. Producer 설정을 담은 Factory Bean
    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        String bootstrapServer = System.getenv("SPRING_KAFKA_BOOTSTRAP_SERVERS");
        if (bootstrapServer == null) {
            bootstrapServer = "localhost:9092";
        }
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    // 2. 위에서 만든 factory를 사용하는 Template Bean
    // 메서드 이름을 하나만 남기고 중복된 @Bean kafkaTemplate 메서드는 지우세요!
    @Bean
    @Primary
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
