package com.turkcell.product_service.infrastructure.persistence.outbox.entity;

public enum ProductOutboxStatus {
    PENDING,
    SENT,
    FAILED
}
