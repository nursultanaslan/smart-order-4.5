package com.turkcell.order_service.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

public record Money(BigDecimal value, String currency) {
    public Money {
        Objects.requireNonNull(value, "Price value must not be null");
        Objects.requireNonNull(currency, "Currency must not be null");

        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price must not be negative");
        }
        if (currency.isBlank()) {
            throw new IllegalArgumentException("Currency must not be blank");
        }
    }
}
