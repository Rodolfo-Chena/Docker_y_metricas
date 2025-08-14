package com.example.hexagonalorders.domain.model;

import com.example.hexagonalorders.domain.model.valueobject.ProductNumber;
import com.example.hexagonalorders.domain.model.valueobject.Quantity;
import java.math.BigDecimal;
//import java.util.Objects;

/**
 * Core domain entity representing an OrderItem in the system.
 * This class is part of the domain layer and represents a line item within an Order.
 * It is independent of any infrastructure concerns.
 * 
 * An OrderItem consists of:
 * - A unique identifier
 * - A product number (as a value object)
 * - A quantity (as a value object)
 */
public class OrderItem {
    private final ProductNumber productNumber;
    private final Quantity quantity;
    private final BigDecimal unitPrice;

    public OrderItem(ProductNumber productNumber, Quantity quantity, BigDecimal unitPrice) {
        if (productNumber == null) {
            throw new IllegalArgumentException("Product number cannot be null");
        }
        if (quantity == null) {
            throw new IllegalArgumentException("Quantity cannot be null");
        }
        if (unitPrice == null || unitPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Unit price must be greater than zero");
        }
        this.productNumber = productNumber;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public ProductNumber getProductNumber() {
        return productNumber;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    // equals, hashCode, and toString can be added as needed
} 