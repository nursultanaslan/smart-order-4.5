package com.turkcell.product_service.domain.exception;

import com.turkcell.product_service.domain.exception.base.DomainException;

public class InvalidCategoryNameException extends DomainException {
    public InvalidCategoryNameException(String message) {
        super(message);
    }
}
