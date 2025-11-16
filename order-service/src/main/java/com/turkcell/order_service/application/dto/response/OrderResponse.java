package com.turkcell.order_service.application.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderResponse(
        UUID orderId,
        BigDecimal totalPrice,
        String currency
) {
}
