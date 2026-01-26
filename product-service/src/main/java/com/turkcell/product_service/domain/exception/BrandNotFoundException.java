package com.turkcell.product_service.domain.exception;

public class BrandNotFoundException extends RuntimeException {
    public BrandNotFoundException(String message) {
        super(message);
    }
}
