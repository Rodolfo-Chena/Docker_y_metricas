package com.example.hexagonalorders.domain.port.in;

import com.example.hexagonalorders.domain.model.Order;
import com.example.hexagonalorders.domain.model.valueobject.OrderNumber;

import java.util.List;
import java.util.Optional;

/**
 * Input port defining the contract for order operations.
 * This interface is part of the domain layer and defines the core business operations
 * that can be performed on orders.
 */
public interface OrderUseCase {
    /**
     * Creates a new order
     * @param order the order to create
     * @return the created order
     */
    Order createOrder(Order order);

    /**
     * Retrieves an order by its order number
     * @param orderNumber the order number
     * @return the order if found
     */
    Optional<Order> getOrder(OrderNumber orderNumber);

    /**
     * Retrieves all orders
     * @return list of all orders
     */
    List<Order> getAllOrders();

    /**
     * Deletes an order by its order number
     * @param orderNumber the order number
     */
    void deleteOrder(OrderNumber orderNumber);
    /**
     * Aqui confirma la ordern y hace ese cambio de estado de PENDING A CONFIRMED
     * @param orderNumber the order number
     * @return the updated order
     */
    Order confirmOrder(OrderNumber orderNumber);
} 