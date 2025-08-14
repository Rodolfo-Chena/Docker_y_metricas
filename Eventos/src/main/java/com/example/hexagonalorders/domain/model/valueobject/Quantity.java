package com.example.hexagonalorders.domain.model.valueobject;

/**
 * Value object representing a product quantity.
 * This encapsulates the business rules and validation for quantities.
 */
public record Quantity(int value) {
    public Quantity {
        if (value <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
    }

    public Quantity add(Quantity other) {
        return new Quantity(this.value + other.value);
    }

    public Quantity subtract(Quantity other) {
        int newValue = this.value - other.value;
        if (newValue <= 0) {
            throw new IllegalArgumentException("Resulting quantity must be greater than zero");
        }
        return new Quantity(newValue);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
} 