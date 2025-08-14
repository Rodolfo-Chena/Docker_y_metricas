package com.example.hexagonalorders.infrastructure.out.persistence.repository;

import com.example.hexagonalorders.infrastructure.out.persistence.entity.OutboxJpaEntity;
import com.example.hexagonalorders.infrastructure.out.persistence.entity.OutboxJpaEntity.OutboxStatusJpa;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OutboxMessageJpaRepository extends JpaRepository<OutboxJpaEntity, UUID> {

    List<OutboxJpaEntity> findTop10ByStatusOrderByCreatedAtAsc(OutboxStatusJpa status, Pageable pageable);
}
