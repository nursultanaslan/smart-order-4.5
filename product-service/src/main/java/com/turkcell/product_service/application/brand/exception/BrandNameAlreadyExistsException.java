package com.turkcell.product_service.application.brand.exception;

public class BrandNameAlreadyExistsException extends RuntimeException {
    public BrandNameAlreadyExistsException(String message) {
        super(message);
    }
}
