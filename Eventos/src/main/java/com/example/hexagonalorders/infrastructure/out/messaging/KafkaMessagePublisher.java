package com.example.hexagonalorders.infrastructure.out.messaging;

import com.example.hexagonalorders.domain.port.out.MessagePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaMessagePublisher implements MessagePublisher {

    private final KafkaTemplate<String,String> kafkaTemplate;
    private final TopicNameMapper topicNameMapper;

    @Override
    public void publish(String topic, String payload) {
        try {
            String[] parts = topic.split("\\.");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Topic debe ser 'Aggregate.EventType' (recibido: " + topic + ")");
            }
            String aggregate = parts[0];
            String eventType = parts[1];

            String kafkaTopic = topicNameMapper.mapToTopicName(aggregate, eventType);

            CompletableFuture<SendResult<String,String>> fut = kafkaTemplate.send(kafkaTopic, eventType, payload);
            fut.whenComplete((res, ex) -> {
                if (ex != null) {
                    log.error("Kafka publish failed to {}: {}", kafkaTopic, ex.getMessage(), ex);
                } else {
                    log.debug("Kafka publish OK topic={} partition={} offset={}",
                            kafkaTopic,
                            res.getRecordMetadata().partition(),
                            res.getRecordMetadata().offset());
                }
            });

            log.info("Published integration event to Kafka topic='{}' (aggregate={}, eventType={})",
                    kafkaTopic, aggregate, eventType);

        } catch (Exception e) {
            throw new RuntimeException("Failed to publish message to Kafka", e);
        }
    }
}
