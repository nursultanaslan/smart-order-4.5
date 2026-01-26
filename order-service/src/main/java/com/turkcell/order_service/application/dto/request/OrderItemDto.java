package com.turkcell.order_service.application.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemDto(
        UUID productId,
        String productName,
        Integer quantity,
        BigDecimal price,
        String currency) {
}
