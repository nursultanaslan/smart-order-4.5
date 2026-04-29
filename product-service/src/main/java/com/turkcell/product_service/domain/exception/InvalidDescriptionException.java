package com.turkcell.product_service.domain.exception;

import com.turkcell.product_service.domain.exception.base.DomainException;

public class InvalidDescriptionException extends DomainException {
    public InvalidDescriptionException(String message) {
        super(message);
    }
}
