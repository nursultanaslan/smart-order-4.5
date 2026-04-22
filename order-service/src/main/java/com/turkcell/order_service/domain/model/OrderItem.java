package com.turkcell.order_service.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.UUID;

public record OrderItem(
        UUID productId,
        String productName,
        BigDecimal unitPriceAtOrderTime,
        String currency,
        Integer quantity) {

    public OrderItem {
        Objects.requireNonNull(productId, "Product Id cannot be null");
        Objects.requireNonNull(productName, "Product Name cannot be null");
        Objects.requireNonNull(currency, "Currency cannot be null");
        Objects.requireNonNull(unitPriceAtOrderTime, "Unit Price cannot be null");
        Objects.requireNonNull(quantity, "Quantity cannot be null");
        if (productName.isBlank()) {
            throw new IllegalArgumentException("Product Name cannot be blank");
        }
        if (currency.isBlank()) {
            throw new IllegalArgumentException("Currency cannot be blank");
        }
        if (unitPriceAtOrderTime.signum() < 0) {
            throw new IllegalArgumentException("Unit Price cannot be negative");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
    }

    public BigDecimal calculateLineTotal() {
        return unitPriceAtOrderTime
                .multiply(BigDecimal.valueOf(quantity))
                .setScale(2, RoundingMode.HALF_UP);
    }
}
