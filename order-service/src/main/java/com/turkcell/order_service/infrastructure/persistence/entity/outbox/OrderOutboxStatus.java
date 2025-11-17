package com.turkcell.order_service.infrastructure.persistence.entity.outbox;

public enum OrderOutboxStatus {
    PENDING,
    SENT,
    FAILED,
}
