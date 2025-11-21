package com.turkcell.cart_service.domain.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public record CustomerId(UUID value) implements Serializable {
    public CustomerId {
        Objects.requireNonNull(value, "Value for customer id cannot be null");
    }

    public static CustomerId generate() {
        return new CustomerId(UUID.randomUUID());
    }
}
