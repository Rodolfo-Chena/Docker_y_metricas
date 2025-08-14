package com.example.hexagonalorders.infrastructure.out.event;

import com.example.hexagonalorders.domain.model.OutboxMessage;
import com.example.hexagonalorders.domain.port.out.MessagePublisher;
import com.example.hexagonalorders.domain.port.out.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxProcessor {

    private final OutboxRepository outboxRepository;
    private final MessagePublisher messagePublisher;

    @Scheduled(fixedDelayString = "${outbox.poll.ms:1000}")
    @Transactional
    public void processPendingMessages() {
        List<OutboxMessage> pendingMessages = outboxRepository.findPending(10);

        for (OutboxMessage message : pendingMessages) {
           if (!message.eventType().endsWith("IntegrationEvent")) {
                outboxRepository.markProcessed(message.id()); // no lo publiques
            continue;
            }
            try {
                String topic = message.aggregateType() + "." + message.eventType();
                messagePublisher.publish(topic, message.payload());
                outboxRepository.markProcessed(message.id());
            } catch (Exception e) {
                log.error("Failed to publish outbox message {}", message.id(), e);
                outboxRepository.markFailed(message.id());
            }
        }
    }
}
