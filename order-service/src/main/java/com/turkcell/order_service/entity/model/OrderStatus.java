package com.turkcell.order_service.entity.model;

public enum OrderStatus {
    PENDING,
    PAYMENT_FAILED,
    WAITING_SHIPMENT,
    SHIPPED,
    COMPLETED,
}
