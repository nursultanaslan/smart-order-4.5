package com.turkcell.order_service.domain.model;

import java.util.Objects;
import java.util.UUID;

public record OrderLine(
        UUID productId,
        String productName,
        Long unitPrice,
        String currency,
        Integer quantity,
        Long lineTotalPrice
) {
    public OrderLine{
        Objects.requireNonNull(productId,  "Product Id cannot be null");
        Objects.requireNonNull(productName,  "Product Name cannot be null");
        Objects.requireNonNull(currency,"Currency cannot be null");
        if (currency.isBlank()){
            throw new IllegalArgumentException("Currency cannot be blank");
        }
        if (unitPrice < 0){
            throw new IllegalArgumentException("Unit Price cannot be negative");
        }
        if (quantity <= 0){
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        if (lineTotalPrice < 0){
            throw new IllegalArgumentException("Line Total Price cannot be negative");
        }
    }
}
