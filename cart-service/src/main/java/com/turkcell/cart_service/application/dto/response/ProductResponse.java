package com.turkcell.cart_service.application.dto.response;

import java.math.BigDecimal;

public record ProductResponse(
        Integer stock,
        BigDecimal quantity,
        String currency
) {
}
