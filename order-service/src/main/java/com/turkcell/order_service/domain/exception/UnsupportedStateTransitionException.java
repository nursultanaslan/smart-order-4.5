package com.turkcell.order_service.domain.exception;

import com.turkcell.order_service.domain.aggregate.enums.OrderStatus;

public class UnsupportedStateTransitionException extends RuntimeException {
    public UnsupportedStateTransitionException(OrderStatus status) {
        super("current state: " + status);
    }

    public UnsupportedStateTransitionException(OrderStatus currentStatus, OrderStatus targetStatus) {
        super("Cannot transition from " + currentStatus + " to " + targetStatus);
    }
}
