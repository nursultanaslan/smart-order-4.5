package com.turkcell.product_service.domain.model.product;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public record ProductId(UUID value) implements Serializable {
    public ProductId {
        Objects.requireNonNull(value, "Value must not be null");
    }

    public static ProductId generate() {
        return new ProductId(UUID.randomUUID());
    }
}
