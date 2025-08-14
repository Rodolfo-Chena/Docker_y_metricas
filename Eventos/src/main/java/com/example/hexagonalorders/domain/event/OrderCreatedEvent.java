package com.example.hexagonalorders.domain.event;

import com.example.hexagonalorders.domain.model.valueobject.OrderNumber;

/**
 * Event raised when a new order is created.
 */
public class OrderCreatedEvent extends DomainEvent {
    private final Long orderId;
    private final OrderNumber orderNumber;

    public OrderCreatedEvent(Long orderId, OrderNumber orderNumber) {
        super();
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