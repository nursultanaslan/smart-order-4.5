package com.turkcell.order_service.entity.messaging.outbox;

public enum OutboxStatus {
    PENDING,
    SENT,
    FAILED,
}
