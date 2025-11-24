package com.turkcell.product_service.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String name,
        BigDecimal amount,
        String currency,
        String description,
        Integer stock
) {
}
