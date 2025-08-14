package com.example.hexagonalorders.infrastructure.out.persistence.mapper;

import com.example.hexagonalorders.domain.model.OutboxMessage;
import com.example.hexagonalorders.infrastructure.out.persistence.entity.OutboxJpaEntity;
import com.example.hexagonalorders.infrastructure.out.persistence.entity.OutboxJpaEntity.OutboxStatusJpa;

public class OutboxMessageMapper {

    public static OutboxJpaEntity toJpaEntity(OutboxMessage message) {
        OutboxJpaEntity entity = new OutboxJpaEntity();
        entity.setId(message.id());
        entity.setAggregateType(message.aggregateType());
        entity.setAggregateId(message.aggregateId());
        entity.setEventType(message.eventType());
        entity.setPayload(message.payload());
        entity.setStatus(mapStatusToJpa(message.status()));
        entity.setCreatedAt(message.createdAt());
        entity.setProcessedAt(message.processedAt());
        return entity;
    }

    public static OutboxMessage toDomainModel(OutboxJpaEntity entity) {
        return new OutboxMessage(
            entity.getId(),
            entity.getAggregateType(),
            entity.getAggregateId(),
            entity.getEventType(),
            entity.getPayload(),
            mapStatusToDomain(entity.getStatus()),
            entity.getCreatedAt(),
            entity.getProcessedAt()
        );
    }

    private static OutboxStatusJpa mapStatusToJpa(OutboxMessage.Status status) {
        return OutboxStatusJpa.valueOf(status.name());
    }

    private static OutboxMessage.Status mapStatusToDomain(OutboxStatusJpa status) {
        return OutboxMessage.Status.valueOf(status.name());
    }
}
