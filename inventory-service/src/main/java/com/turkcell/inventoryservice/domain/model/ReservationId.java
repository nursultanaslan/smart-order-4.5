package com.turkcell.inventoryservice.domain.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public record ReservationId(UUID value) implements Serializable {
    public ReservationId {
        Objects.requireNonNull( value, "ReservationId value must not be null!");
    }

    public static ReservationId generate() {
        return new ReservationId(UUID.randomUUID());
    }
}
