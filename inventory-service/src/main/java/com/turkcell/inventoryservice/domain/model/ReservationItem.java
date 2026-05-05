package com.turkcell.inventoryservice.domain.model;

import java.util.Objects;

public record ReservationItem(ProductId productId, Integer quantity) {
    public ReservationItem {
        Objects.requireNonNull(quantity, "Quantity can't be null");

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive" + quantity);
        }
    }
}
