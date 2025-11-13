package com.turkcell.order_service.infrastructure.messaging.outbox;

public enum OutboxStatus {
    PENDING,
    SENT,
    FAILED,
}
