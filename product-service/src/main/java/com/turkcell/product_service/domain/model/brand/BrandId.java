package com.turkcell.product_service.domain.model.brand;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

//VO
public record BrandId(UUID value) implements Serializable {

    public BrandId {
        Objects.requireNonNull(value, "value for BrandId must not be null");
    }

    public static BrandId generate() {
        return new BrandId(UUID.randomUUID());
    }
}
