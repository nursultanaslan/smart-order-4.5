package com.turkcell.order_service.domain.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public record OrderId(UUID value) implements Serializable {
    public OrderId{
        Objects.requireNonNull(value, "OrderId not null");
    }

    public static OrderId generate(){
        return new OrderId(UUID.randomUUID());
    }
}
