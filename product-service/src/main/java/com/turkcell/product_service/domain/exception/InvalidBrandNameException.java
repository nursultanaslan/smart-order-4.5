package com.turkcell.product_service.domain.exception;

import com.turkcell.product_service.domain.exception.base.DomainException;

public class InvalidBrandNameException extends DomainException {
    public InvalidBrandNameException(String message) {
        super(message);
    }
}
