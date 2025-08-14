package com.example.hexagonalorders.application.handler;

import com.example.hexagonalorders.application.event.OrderConfirmedIntegrationEvent;
import com.example.hexagonalorders.domain.event.DomainEvent;
import com.example.hexagonalorders.domain.event.OrderConfirmedEvent;
import com.example.hexagonalorders.domain.model.OutboxMessage;
import com.example.hexagonalorders.domain.port.out.OutboxRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class DomainEventHandler {

    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;

    @EventListener
    public void handleOrderConfirmed(OrderConfirmedEvent event) {
        try {
            OrderConfirmedIntegrationEvent integration =
                    new OrderConfirmedIntegrationEvent(event.getOrderNumber());
            persistToOutbox(integration, "Order", event.getOrderNumber().value());
        } catch (Exception e) {
            throw new RuntimeException("Failed to process order confirmation integration event", e);
        }
    }

    @EventListener
    public void handleGenericDomainEvent(DomainEvent event) {
        log.debug("Domain event received (no integration mapping): {}", event.getClass().getSimpleName());
    }

    private void persistToOutbox(Object event, String aggregateType, String aggregateId) {
        try {
            String payload = objectMapper.writeValueAsString(event);
            String eventType = event.getClass().getSimpleName();
            UUID uuid = UUID.nameUUIDFromBytes(aggregateId.getBytes());
            OutboxMessage msg = OutboxMessage.createPendingMessage(aggregateType, uuid, eventType, payload);
            outboxRepository.save(msg);
        } catch (Exception e) {
            throw new RuntimeException("Failed to persist event to outbox", e);
        }
    }
}
