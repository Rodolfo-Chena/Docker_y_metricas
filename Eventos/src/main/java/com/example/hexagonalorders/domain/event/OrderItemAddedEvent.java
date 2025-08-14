package com.example.hexagonalorders.domain.event;

import com.example.hexagonalorders.domain.model.valueobject.ProductNumber;
import com.example.hexagonalorders.domain.model.valueobject.Quantity;

/**
 * Event raised when an item is added to an order.
 */
public class OrderItemAddedEvent extends DomainEvent {
    private final Long orderId;
    private final Long itemId;
    private final ProductNumber productNumber;
    private final Quantity quantity;

    public OrderItemAddedEvent(Long orderId, Long itemId, ProductNumber productNumber, Quantity quantity) {
        super();
        this.orderId = orderId;
        this.itemId = itemId;
        this.productNumber = productNumber;
        this.quantity = quantity;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getItemId() {
        return itemId;
    }

    public ProductNumber getProductNumber() {
        return productNumber;
    }

    public Quantity getQuantity() {
        return quantity;
    }
} 