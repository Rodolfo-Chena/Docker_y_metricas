package com.example.hexagonalorders.domain.event;

import com.example.hexagonalorders.domain.model.valueobject.OrderNumber;

/**
 * Event raised when an order is confirmed.
 */
public class OrderConfirmedEvent extends DomainEvent {

    private final Long orderId;
    private final OrderNumber orderNumber;

    public OrderConfirmedEvent(Long orderId, OrderNumber orderNumber) {
        super(); // Inicializa datos comunes del DomainEvent (timestamp, id, etc.)
        this.orderId = orderId;
        this.orderNumber = orderNumber;
    }

    public Long getOrderId() {
        return orderId;
    }

    public OrderNumber getOrderNumber() {
        return orderNumber;
    }
}
