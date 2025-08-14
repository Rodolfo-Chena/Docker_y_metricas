package com.example.hexagonalorders.domain.port.out;

import com.example.hexagonalorders.domain.model.valueobject.OrderNumber;

/**
 * Output port defining the contract for order number generation.
 * This interface is part of the domain layer and defines how the domain
 * expects to generate unique order numbers.
 * 
 * Implementations of this port should be provided by the adapter layer
 * (e.g., using UUID, sequence numbers, etc.).
 */
public interface OrderNumberGenerator {
    OrderNumber generate();
} 