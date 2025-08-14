package com.example.hexagonalorders.infrastructure.out.persistence.repository;

import com.example.hexagonalorders.domain.model.Order;
import com.example.hexagonalorders.domain.model.valueobject.OrderNumber;
import com.example.hexagonalorders.domain.port.out.OrderRepository;
import com.example.hexagonalorders.infrastructure.out.persistence.entity.OrderJpaEntity;
import com.example.hexagonalorders.infrastructure.out.persistence.mapper.OrderJpaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderJpaMapper orderJpaMapper;

    @Override
    public Order save(Order order) {
        // Mapear dominio -> entidad JPA
        OrderJpaEntity entity = orderJpaMapper.toEntity(order);

        // --- CLAVE: forzar UPDATE si ya existe por orderNumber ---
        Optional<OrderJpaEntity> existingOpt = orderJpaRepository.findByOrderNumber(order.getOrderNumber().value());
        existingOpt.ifPresent(existing -> {
            // copiar ID para que JPA haga merge/update en vez de insert
            entity.setId(existing.getId());
            // si sustituyes la lista de items, asegúrate de que el mapeo tenga orphanRemoval/cascade;
            // si no, podrías limpiar y volver a agregar según tu estrategia.
        });

        // Guardar (INSERT si no existía; UPDATE si existía)
        OrderJpaEntity saved = orderJpaRepository.save(entity);

        // devolver dominio
        return orderJpaMapper.toDomain(saved);
    }

    @Override
    public Optional<Order> findByOrderNumber(OrderNumber orderNumber) {
        return orderJpaRepository.findByOrderNumber(orderNumber.value())
                .map(orderJpaMapper::toDomain);
    }

    @Override
    public List<Order> findAll() {
        return orderJpaRepository.findAll().stream()
                .map(orderJpaMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByOrderNumber(OrderNumber orderNumber) {
        orderJpaRepository.deleteByOrderNumber(orderNumber.value());
    }
}
