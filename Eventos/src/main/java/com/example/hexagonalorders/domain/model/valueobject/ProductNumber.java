package com.example.hexagonalorders.domain.model.valueobject;

/**
 * Value object representing a product number.
 * This encapsulates the business rules and validation for product numbers.
 */
public record ProductNumber(String value) {
    public ProductNumber {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Product number cannot be null or empty");
        }
    }

    @Override
    public String toString() {
        return value;
    }
} 