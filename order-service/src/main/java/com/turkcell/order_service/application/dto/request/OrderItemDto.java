package com.turkcell.order_service.application.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemDto(
        UUID productId,
        String productName,
        BigDecimal unitPriceAtOrderTime,
        String currency,
        Integer quantity,
        BigDecimal lineTotalPrice) {
}
