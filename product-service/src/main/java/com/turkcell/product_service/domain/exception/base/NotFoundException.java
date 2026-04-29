package com.turkcell.product_service.domain.exception.base;

public class NotFoundException extends DomainException {
    public NotFoundException(String message) {
        super(message);
    }
}
