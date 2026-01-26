package com.turkcell.order_service.domain.model;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public record OrderItem(
        UUID productId,
        String productName,
        BigDecimal unitPrice,
        String currency,
        Integer quantity,
        BigDecimal itemTotalPrice) {

    public OrderItem {
        Objects.requireNonNull(productId,  "Product Id cannot be null");
        Objects.requireNonNull(productName,  "Product Name cannot be null");
        Objects.requireNonNull(currency,"Currency cannot be null");
        if (currency.isBlank()){
            throw new IllegalArgumentException("Currency cannot be blank");
        }
        if (unitPrice.signum() < 0){
            throw new IllegalArgumentException("Unit Price cannot be negative");
        }
        if (quantity <= 0){
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        if (itemTotalPrice.signum() < 0){
            throw new IllegalArgumentException("Line Total Price cannot be negative");
        }
    }
}
