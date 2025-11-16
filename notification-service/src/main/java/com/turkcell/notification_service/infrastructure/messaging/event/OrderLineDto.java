package com.turkcell.notification_service.infrastructure.messaging.event;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderLineDto(
        UUID productId,
        String productName,
        BigDecimal unitPrice,
        String currency,
        Integer quantity,
        BigDecimal lineTotalPrice) {
}
