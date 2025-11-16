package com.turkcell.order_service.infrastructure.persistence.entity;

public enum OrderOutboxStatus {
    PENDING,
    SENT,
    FAILED,
}
