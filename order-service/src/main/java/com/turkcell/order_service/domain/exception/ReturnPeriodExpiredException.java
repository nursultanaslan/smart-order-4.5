package com.turkcell.order_service.domain.exception;

public class ReturnPeriodExpiredException extends RuntimeException {
    public ReturnPeriodExpiredException(String message) {
        super(message);
    }
}
