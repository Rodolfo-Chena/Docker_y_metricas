package com.example.hexagonalorders.infrastructure.out.persistence.repository;

import com.example.hexagonalorders.domain.model.OutboxMessage;
import com.example.hexagonalorders.domain.port.out.OutboxRepository;
import com.example.hexagonalorders.infrastructure.out.persistence.entity.OutboxJpaEntity;
import com.example.hexagonalorders.infrastructure.out.persistence.entity.OutboxJpaEntity.OutboxStatusJpa;
import com.example.hexagonalorders.infrastructure.out.persistence.mapper.OutboxMessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OutboxRepositoryAdapter implements OutboxRepository {

    private final OutboxMessageJpaRepository jpaRepository;

    @Override
    public void save(OutboxMessage message) {
        OutboxJpaEntity entity = OutboxMessageMapper.toJpaEntity(message);
        jpaRepository.save(entity);
    }

    @Override
    public List<OutboxMessage> findPending(int limit) {
        List<OutboxJpaEntity> entities = jpaRepository.findTop10ByStatusOrderByCreatedAtAsc(OutboxStatusJpa.PENDING, PageRequest.of(0, limit));
        return entities.stream()
                .map(OutboxMessageMapper::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public void markProcessed(UUID id) {
        jpaRepository.findById(id).ifPresent(entity -> {
            entity.setStatus(OutboxStatusJpa.PROCESSED);
            entity.setProcessedAt(Instant.now());
            jpaRepository.save(entity);
        });
    }

    @Override
    public void markFailed(UUID id) {
        jpaRepository.findById(id).ifPresent(entity -> {
            entity.setStatus(OutboxStatusJpa.FAILED);
            entity.setProcessedAt(Instant.now());
            jpaRepository.save(entity);
        });
    }
}
