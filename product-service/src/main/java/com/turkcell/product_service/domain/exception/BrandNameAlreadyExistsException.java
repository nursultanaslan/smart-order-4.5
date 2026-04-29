package com.turkcell.product_service.domain.exception;

import com.turkcell.product_service.domain.exception.base.DomainException;

public class BrandNameAlreadyExistsException extends DomainException {
    public BrandNameAlreadyExistsException(String message) {
        super(message);
    }
}
