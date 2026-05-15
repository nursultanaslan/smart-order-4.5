package com.turkcell.order_service.domain.aggregate;

public enum OrderStatus {
    PENDING,
    CONFIRMED,
    PREPARED,
    SHIPPED,
    DELIVERED,
    CANCELLED,
    RETURNED,
    COMPLETED;

    public static OrderStatus getDefault() {
        return PENDING;
    }
}
