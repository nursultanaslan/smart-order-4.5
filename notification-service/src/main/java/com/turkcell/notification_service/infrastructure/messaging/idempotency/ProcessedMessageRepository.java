package com.turkcell.notification_service.infrastructure.messaging.idempotency;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProcessedMessageRepository extends JpaRepository<ProcessedMessage, UUID> {
    Optional<ProcessedMessage> findByMessageIdAndConsumerId(UUID messageId, UUID consumerId);
}
