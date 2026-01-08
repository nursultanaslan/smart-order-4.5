package com.turkcell.product_service.infrastructure.persistence.outbox;

public enum ProductOutboxStatus {
    PENDING,
    SENT,
    FAILED
}
