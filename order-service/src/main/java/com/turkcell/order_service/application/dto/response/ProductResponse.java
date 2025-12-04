package com.turkcell.order_service.application.dto.response;

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
