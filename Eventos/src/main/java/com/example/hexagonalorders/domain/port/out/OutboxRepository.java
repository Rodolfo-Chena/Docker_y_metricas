package com.example.hexagonalorders.domain.port.out;

import com.example.hexagonalorders.domain.model.OutboxMessage;

import java.util.List;
import java.util.UUID;

public interface OutboxRepository {

    void save(OutboxMessage message);

    List<OutboxMessage> findPending(int limit);

    void markProcessed(UUID id);

    void markFailed(UUID id);
}
