package com.example.hexagonalorders.infrastructure.out.persistence.repository;

import com.example.hexagonalorders.infrastructure.out.persistence.entity.OrderJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderJpaEntity, Long> {
    Optional<OrderJpaEntity> findByOrderNumber(String orderNumber);
    void deleteByOrderNumber(String orderNumber);
} 
