package com.turkcell.order_service.infrastructure.persistence.entity;

public enum OutboxStatus {
    PENDING,
    SENT,
    FAILED,
}
