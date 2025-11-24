package com.turkcell.customer_service.domain.model;

import java.util.Objects;

public record Phone(String value) {

    public Phone {
        Objects.requireNonNull(value, "Phone cannot be null");

        if (value.isBlank()) {
            throw new IllegalArgumentException("Phone cannot be blank");
        }
        if (!value.matches("^(\\+90|90|0)?5\\d{9}$")){
            throw new IllegalArgumentException("Phone number is invalid");
        }
    }
}
