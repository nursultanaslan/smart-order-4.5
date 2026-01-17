package com.turkcell.cart_service.application.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

public record CartItemRequest(
        UUID productId,
        Integer quantity,
        BigDecimal unitPrice
) {
}
