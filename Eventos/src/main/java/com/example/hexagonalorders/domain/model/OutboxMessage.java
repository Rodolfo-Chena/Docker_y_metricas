package com.example.hexagonalorders.domain.model;

import java.time.Instant;
import java.util.UUID;

public record OutboxMessage(
    UUID id,
    String aggregateType,
    UUID aggregateId,
    String eventType,
    String payload,
    Status status,
    Instant createdAt,
    Instant processedAt
) {
    public static OutboxMessage createPendingMessage(String aggregateType, UUID aggregateId, String eventType, String payload) {
        return new OutboxMessage(
            UUID.randomUUID(),
            aggregateType,
            aggregateId,
            eventType,
            payload,
            Status.PENDING,
            Instant.now(),
            null
        );
    }

    public enum Status {
        PENDING, PROCESSED, FAILED
    }
}
