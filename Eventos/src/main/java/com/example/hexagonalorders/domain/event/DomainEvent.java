package com.example.hexagonalorders.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Base class for all domain events.
 * This provides common functionality and metadata for all domain events.
 */
public abstract class DomainEvent {
    private final UUID eventId;
    private final LocalDateTime occurredOn;

    protected DomainEvent() {
        this.eventId = UUID.randomUUID();
        this.occurredOn = LocalDateTime.now();
    }

    public UUID getEventId() {
        return eventId;
    }

    public LocalDateTime getOccurredOn() {
        return occurredOn;
    }
} 