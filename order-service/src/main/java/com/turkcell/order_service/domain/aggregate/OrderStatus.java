package com.turkcell.order_service.domain.aggregate;

public enum OrderStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED,
    CLAIMED;

    public static OrderStatus getDefault() {
        return PENDING;
    }
}
