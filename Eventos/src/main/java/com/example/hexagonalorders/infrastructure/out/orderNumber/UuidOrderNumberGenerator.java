package com.example.hexagonalorders.infrastructure.out.orderNumber;

import com.example.hexagonalorders.domain.model.valueobject.OrderNumber;
import com.example.hexagonalorders.domain.port.out.OrderNumberGenerator;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Secondary adapter implementing the OrderNumberGenerator port using UUID.
 * This class is part of the adapter layer and is responsible for:
 * - Generating unique order numbers using UUID
 * - Implementing the OrderNumberGenerator port
 * - Providing a concrete implementation of the order number generation strategy
 * 
 * This adapter follows the Adapter pattern to provide a specific implementation
 * of the order number generation strategy while keeping the domain layer
 * independent of the implementation details.
 */
@Component
public class UuidOrderNumberGenerator implements OrderNumberGenerator {
    @Override
    public OrderNumber generate() {
        return new OrderNumber(UUID.randomUUID().toString());
    }
} 