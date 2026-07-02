package com.turkcell.order_service.domain.aggregate.enums;

public enum OrderStatus {
    APPROVAL_PENDING,   //pending state is an example of a semantic lock countermeasure
    APPROVED,
    REJECTED,
    PREPARING,
    SHIPPED,
    DELIVERED,
    CANCEL_PENDING,
    CANCELLED,
    RETURN_REQUESTED,
    RETURNED,
    COMPLETED;
    public static OrderStatus getDefault() {
        return APPROVAL_PENDING;
    }
}
