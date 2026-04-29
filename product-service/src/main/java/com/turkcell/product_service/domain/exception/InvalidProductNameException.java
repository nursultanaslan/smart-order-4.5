package com.turkcell.product_service.domain.exception;

import com.turkcell.product_service.domain.exception.base.DomainException;

public class InvalidProductNameException extends DomainException {
    public InvalidProductNameException(String message) {
        super(message);
    }
}
