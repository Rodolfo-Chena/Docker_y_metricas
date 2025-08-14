package com.example.hexagonalorders.infrastructure.out.messaging;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** Convierte "Aggregate.EventType" -> nombre real de tópico Kafka con prefijo/ambiente. */
@Component
public class TopicNameMapper {

    @Value("${kafka.topic.prefix:hexagonal-orders}")
    private String prefix;

    @Value("${kafka.topic.environment:dev}")
    private String env;

    private final Map<String,String> mappings = new ConcurrentHashMap<>();

    public TopicNameMapper() {
        mappings.put("Order.OrderConfirmedIntegrationEvent", "order-confirmed");
    }

    public String mapToTopicName(String aggregateType, String eventType) {
        String full = aggregateType + "." + eventType;
        String custom = mappings.get(full);
        String base = (custom != null)
                ? custom
                : aggregateType.toLowerCase() + "-" + eventType.replace("IntegrationEvent","").toLowerCase();
        return String.format("%s-%s-%s", prefix, env, base);
    }
}
