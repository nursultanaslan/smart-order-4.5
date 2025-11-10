package com.turkcell.order_service.infrastructure.persistence.entity.messaging.outbox;

public enum OutboxStatus {
    PENDING,
    SENT,
    FAILED,
}
