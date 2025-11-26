package com.turkcell.product_service.domain.model.category;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public record CategoryId(UUID value) implements Serializable {

    public CategoryId {
        Objects.requireNonNull(value, "value for CategoryId must not be null");
    }

    public static CategoryId generate() {
        return new CategoryId(UUID.randomUUID());
    }
}
