package com.turkcell.product_service.domain.exception;

import com.turkcell.product_service.domain.exception.base.DomainException;

public class InsufficientStockException extends DomainException {
    public InsufficientStockException() {
        super("Insufficient stock!");
    }
}
