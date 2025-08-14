package com.example.hexagonalorders.application.event;

import com.example.hexagonalorders.domain.model.valueobject.OrderNumber;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class OrderConfirmedIntegrationEvent {
    private final String orderNumber;
    private final String eventType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime confirmedAt;

    public OrderConfirmedIntegrationEvent(OrderNumber orderNumber) {
        this.orderNumber = orderNumber.value();
        this.eventType = "OrderConfirmed";
        this.confirmedAt = LocalDateTime.now();
    }

    public String getOrderNumber() { return orderNumber; }
    public String getEventType() { return eventType; }
    public LocalDateTime getConfirmedAt() { return confirmedAt; }
}
