package com.example.hexagonalorders.infrastructure.in.web.mapper;

import com.example.hexagonalorders.domain.model.Order;
import com.example.hexagonalorders.domain.model.OrderItem;
import com.example.hexagonalorders.domain.model.valueobject.OrderNumber;
import com.example.hexagonalorders.domain.model.valueobject.ProductNumber;
import com.example.hexagonalorders.domain.model.valueobject.Quantity;
import com.example.hexagonalorders.domain.model.OrderStatus;
import com.example.hexagonalorders.infrastructure.in.web.dto.OrderDto;
import com.example.hexagonalorders.infrastructure.in.web.dto.OrderItemDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper class responsible for converting between domain entities and DTOs.
 * This class is part of the adapter layer and helps maintain
 * the separation between the domain model and the API layer.
 */
@Component
public class OrderMapper {
    
    public OrderDto toDto(Order order) {
        if (order == null) {
            return null;
        }
        return new OrderDto(
            order.getOrderNumber().value(),
            order.getCustomerId(),
            order.getOrderDate(),
            toItemDtos(order.getItems()),
            order.getStatus().name()
        );
    }
    
    public Order toDomain(OrderDto dto) {
        if (dto == null) {
            return null;
        }
        return new Order(
            new OrderNumber(dto.getOrderNumber()),
            dto.getCustomerId(),
            dto.getOrderDate(),
            toDomainItems(dto.getItems()),
            OrderStatus.valueOf(dto.getStatus())
        );
    }
    
    private List<OrderItemDto> toItemDtos(List<OrderItem> items) {
        if (items == null) {
            return null;
        }
        return items.stream()
                .map(this::toItemDto)
                .collect(Collectors.toList());
    }
    
    private OrderItemDto toItemDto(OrderItem item) {
        if (item == null) {
            return null;
        }
        return new OrderItemDto(
            item.getProductNumber().value(),
            item.getQuantity().value(),
            item.getUnitPrice()
        );
    }
    
    private List<OrderItem> toDomainItems(List<OrderItemDto> itemDtos) {
        if (itemDtos == null) {
            return null;
        }
        return itemDtos.stream()
                .map(this::toDomainItem)
                .collect(Collectors.toList());
    }
    
    private OrderItem toDomainItem(OrderItemDto dto) {
        if (dto == null) {
            return null;
        }
        return new OrderItem(
            new ProductNumber(dto.getProductNumber()),
            new Quantity(dto.getQuantity()),
            dto.getUnitPrice()
        );
    }
} 