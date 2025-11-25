package com.turkcell.product_service.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductDto(
        UUID productId,
        String productName,
        Integer stock,
        BigDecimal amount,
        String currency
) {
}
