package com.example.delivery.infrastructure.in.messaging;

import com.example.delivery.application.event.OrderConfirmedIntegrationEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class OrderConfirmedConsumer {

  private final ObjectMapper objectMapper;

  public OrderConfirmedConsumer(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper; // Usa el ObjectMapper de Spring (con JavaTimeModule)
  }

  @KafkaListener(topics = "hexagonal-orders-dev-order-confirmed", groupId = "delivery-service")
  public void onMessage(@Payload String payload) {
    try {
      OrderConfirmedIntegrationEvent evt =
          objectMapper.readValue(payload, OrderConfirmedIntegrationEvent.class);
      System.out.printf("📦 Delivery recibido: orderNumber=%s at=%s (eventType=%s)%n",
          evt.getOrderNumber(), evt.getConfirmedAt(), evt.getEventType());
      // TODO: aquí tu lógica createDelivery(...)
    } catch (Exception e) {
      System.err.println("Error parseando evento OrderConfirmed: " + payload);
      e.printStackTrace();
    }
  }
}
