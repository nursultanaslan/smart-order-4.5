package com.turkcell.order_service.domain.aggregate;

public enum OrderStatus {
    APPROVAL_PENDING,
    APPROVED,
    PREPARING,
    SHIPPED,
    DELIVERED,
    CANCEL_PENDING,
    CANCELLED,
    RETURNED,
    COMPLETED;

    public static OrderStatus getDefault() {
        return APPROVAL_PENDING;
    }
}
