package com.turkcell.product_service.application.product.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponse(
        UUID productId,
        String productName,
        BigDecimal amount,
        String currency,
        String description,
        Integer stock
) {
}
