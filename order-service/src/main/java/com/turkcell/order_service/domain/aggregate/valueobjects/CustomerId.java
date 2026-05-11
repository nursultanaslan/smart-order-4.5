package com.turkcell.order_service.domain.aggregate.valueobjects;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public record CustomerId(UUID value) implements Serializable {
    public CustomerId {
        Objects.requireNonNull(value, "value for customerId must not be null");
    }

}
