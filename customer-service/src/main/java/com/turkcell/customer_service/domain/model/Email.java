package com.turkcell.customer_service.domain.model;

import java.util.Objects;

public record Email(String value) {
    public Email {
        Objects.requireNonNull(value, "Email cannot be null");

        if (value.isBlank()){
            throw new IllegalArgumentException("Email cannot be blank");
        }
        if (!value.matches("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$")){
            throw new IllegalArgumentException("Email contains invalid characters");
        }
    }
}
