package com.turkcell.notification_service.infrastructure.messaging.idempotency;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "processed_messages")
public class ProcessedMessage {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name = "message_id", nullable = false)
    private UUID messageId;
    @Column(name = "consumer_id", nullable = false)
    private UUID consumerId;

    public ProcessedMessage() {
    }

    public ProcessedMessage(UUID id, UUID messageId, UUID consumerId) {
        this.id = id;
        this.messageId = messageId;
        this.consumerId = consumerId;
    }

    public UUID id() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID messageId() {
        return messageId;
    }

    public void setMessageId(UUID messageId) {
        this.messageId = messageId;
    }

    public UUID consumerId() {
        return consumerId;
    }

    public void setConsumerId(UUID consumerId) {
        this.consumerId = consumerId;
    }
}
