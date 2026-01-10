package com.turkcell.cart_service.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public record CartItem(
        UUID productId,
        Integer quantity,
        BigDecimal unitPrice
) {
}
