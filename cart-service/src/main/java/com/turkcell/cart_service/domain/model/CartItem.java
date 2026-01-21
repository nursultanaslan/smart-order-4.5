package com.turkcell.cart_service.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public record CartItem(
        UUID productId,
        Integer quantity,
        BigDecimal unitPrice,
        String currency) {

    public CartItem {
        if (quantity <= 0){
            throw new IllegalArgumentException("Quantity should be greater than 0");
        }
        if(unitPrice == null || unitPrice.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Unit Price cannot be null or negative");
        }
    }

    public BigDecimal lineTotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
