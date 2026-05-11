package com.turkcell.order_service.domain.aggregate.valueobjects;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public record CartId(UUID value) implements Serializable {
    public CartId {
        Objects.requireNonNull(value, "value for cartId must not be null");
    }
}
