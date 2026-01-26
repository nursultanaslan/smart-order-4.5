package com.turkcell.product_service.domain.exception;

public class CategoryNameAlreadyExistsException extends RuntimeException {
    public CategoryNameAlreadyExistsException(String message) {
        super(message);
    }
}
