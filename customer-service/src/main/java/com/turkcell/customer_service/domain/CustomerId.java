package com.turkcell.customer_service.domain;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public record CustomerId(UUID value) implements Serializable {

    public CustomerId {
        Objects.requireNonNull(value, "value for customerId must not be null");
    }

    public static CustomerId generate() {
        return new CustomerId(UUID.randomUUID());
    }
}
