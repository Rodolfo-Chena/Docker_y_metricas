package com.example.hexagonalorders.infrastructure.out.persistence.mapper;

import com.example.hexagonalorders.domain.model.Order;
import com.example.hexagonalorders.domain.model.OrderItem;
import com.example.hexagonalorders.domain.model.OrderStatus;
import com.example.hexagonalorders.domain.model.valueobject.OrderNumber;
import com.example.hexagonalorders.domain.model.valueobject.ProductNumber;
import com.example.hexagonalorders.domain.model.valueobject.Quantity;
import com.example.hexagonalorders.infrastructure.out.persistence.entity.OrderItemJpaEntity;
import com.example.hexagonalorders.infrastructure.out.persistence.entity.OrderJpaEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderJpaMapper {

    public OrderJpaEntity toEntity(Order order) {
        OrderJpaEntity entity = new OrderJpaEntity();
        entity.setOrderNumber(order.getOrderNumber().value());
        entity.setCustomerId(order.getCustomerId());
        entity.setOrderDate(order.getOrderDate());
        entity.setStatus(toJpaOrderStatus(order.getStatus()));


        List<OrderItemJpaEntity> itemEntities = toItemJpaEntities(order.getItems());
        itemEntities.forEach(item -> item.setOrder(entity)); // set back-reference
        entity.setItems(itemEntities);

        return entity;
    }

    public Order toDomain(OrderJpaEntity jpaEntity) {
        List<OrderItem> items = jpaEntity.getItems().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());

        return new Order(
                new OrderNumber(jpaEntity.getOrderNumber()),
                jpaEntity.getCustomerId(),
                jpaEntity.getOrderDate(),
                items,
                toDomainOrderStatus(jpaEntity.getStatus())
        );
    }

    private List<OrderItemJpaEntity> toItemJpaEntities(List<OrderItem> items) {
        return items.stream()
                .map(this::toJpaEntity)
                .collect(Collectors.toList());
    }

    private OrderItemJpaEntity toJpaEntity(OrderItem item) {
        OrderItemJpaEntity jpaEntity = new OrderItemJpaEntity();
        jpaEntity.setProductNumber(item.getProductNumber().value());
        jpaEntity.setQuantity(item.getQuantity().value());
        jpaEntity.setUnitPrice(item.getUnitPrice());
        return jpaEntity;
    }

    private OrderItem toDomain(OrderItemJpaEntity jpaEntity) {
        return new OrderItem(
                new ProductNumber(jpaEntity.getProductNumber()),
                new Quantity(jpaEntity.getQuantity()),
                jpaEntity.getUnitPrice()
        );
    }

    private com.example.hexagonalorders.infrastructure.out.persistence.entity.OrderStatus toJpaOrderStatus(OrderStatus status) {
        return com.example.hexagonalorders.infrastructure.out.persistence.entity.OrderStatus.valueOf(status.name());
    }

    private OrderStatus toDomainOrderStatus(com.example.hexagonalorders.infrastructure.out.persistence.entity.OrderStatus status) {
        return OrderStatus.valueOf(status.name());
    }
}
