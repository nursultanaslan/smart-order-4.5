package com.turkcell.product_service.domain.exception;

public class BrandNameAlreadyExistsException extends RuntimeException {
    public BrandNameAlreadyExistsException(String message) {
        super(message);
    }
}
