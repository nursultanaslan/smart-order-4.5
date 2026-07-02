package com.turkcell.order_service.domain.exception;

import com.turkcell.order_service.domain.aggregate.enums.OrderStatus;

public class UnsupportedOrderStateTransitionException extends RuntimeException {
    public UnsupportedOrderStateTransitionException(OrderStatus status) {
        super("current state: " + status);
    }

    public UnsupportedOrderStateTransitionException(OrderStatus currentStatus, OrderStatus targetStatus) {
        super("Cannot transition from " + currentStatus + " to " + targetStatus);
    }
}
