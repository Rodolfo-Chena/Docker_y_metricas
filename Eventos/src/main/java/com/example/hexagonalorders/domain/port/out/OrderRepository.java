package com.example.hexagonalorders.domain.port.out;

import com.example.hexagonalorders.domain.model.Order;
import com.example.hexagonalorders.domain.model.valueobject.OrderNumber;

import java.util.List;
import java.util.Optional;

/**
 * Output port defining the contract for order persistence.
 * This interface is part of the domain layer and defines how
 * the application core interacts with the persistence mechanism.
 */
public interface OrderRepository {
    /**
     * Saves an order
     * @param order the order to save
     * @return the saved order
     */
    Order save(Order order);

    /**
     * Finds an order by its order number
     * @param orderNumber the order number
     * @return the order if found
     */
    Optional<Order> findByOrderNumber(OrderNumber orderNumber);

    /**
     * Retrieves all orders
     * @return list of all orders
     */
    List<Order> findAll();

    /**
     * Deletes an order by its order number
     * @param orderNumber the order number
     */
    void deleteByOrderNumber(OrderNumber orderNumber);
} 