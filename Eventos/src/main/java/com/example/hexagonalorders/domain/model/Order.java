package com.example.hexagonalorders.domain.model;

import com.example.hexagonalorders.domain.event.DomainEvent;
import com.example.hexagonalorders.domain.event.OrderCreatedEvent;
import com.example.hexagonalorders.domain.event.OrderConfirmedEvent;
import com.example.hexagonalorders.domain.event.OrderItemAddedEvent;
import com.example.hexagonalorders.domain.model.valueobject.OrderNumber;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Core domain entity representing an Order in the system.
 * This class is part of the domain layer and contains the business logic and rules
 * related to orders. It is independent of any infrastructure concerns.
 */
public class Order {
    private final OrderNumber orderNumber;
    private final String customerId;
    private final LocalDateTime orderDate;
    private final List<OrderItem> items;
    // Mutable to allow PENDING -> CONFIRMED
    private OrderStatus status;

    private final List<DomainEvent> domainEvents = new ArrayList<>();

    public Order(OrderNumber orderNumber,
                 String customerId,
                 LocalDateTime orderDate,
                 List<OrderItem> items,
                 OrderStatus status) {
        if (customerId == null || customerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer ID cannot be null or empty");
        }
        if (orderDate == null) {
            throw new IllegalArgumentException("Order date cannot be null");
        }
        if (items == null) {
            throw new IllegalArgumentException("Items cannot be null");
        }
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        this.orderNumber = orderNumber;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.items = items;
        this.status = status;

        // Evento inicial
        domainEvents.add(new OrderCreatedEvent(null, orderNumber));
    }

    public OrderNumber getOrderNumber() {
        return orderNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public List<DomainEvent> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    public void clearDomainEvents() {
        domainEvents.clear();
    }

    public void addItem(OrderItem item, Long orderId, Long itemId) {
        if (item == null) {
            throw new IllegalArgumentException("Order item cannot be null");
        }
        this.items.add(item);
        domainEvents.add(new OrderItemAddedEvent(
                orderId,
                itemId,
                item.getProductNumber(),
                item.getQuantity()
        ));
    }

    public void removeItem(OrderItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Order item cannot be null");
        }
        this.items.remove(item);
    }

    /**
     * Confirms the order, transitioning PENDING -> CONFIRMED and raising an OrderConfirmedEvent.
     * @param orderId Persistent identifier (may be null).
     */
    public void confirm(Long orderId) {
        if (this.status != OrderStatus.PENDING) {
            throw new IllegalStateException("Order cannot be confirmed unless it is in PENDING status.");
        }
        this.status = OrderStatus.CONFIRMED;
        domainEvents.add(new OrderConfirmedEvent(orderId, this.orderNumber));
    }
}
