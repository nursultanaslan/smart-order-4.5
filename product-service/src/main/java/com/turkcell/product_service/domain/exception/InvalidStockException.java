package com.turkcell.product_service.domain.exception;

import com.turkcell.product_service.domain.exception.base.DomainException;

public class InvalidStockException extends DomainException {
    public InvalidStockException(String message) {
        super(message);
    }
}
