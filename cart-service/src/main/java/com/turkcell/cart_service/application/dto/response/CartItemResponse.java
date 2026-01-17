package com.turkcell.cart_service.application.dto.response;

import java.math.BigDecimal;

public record CartItemResponse(
        Integer quantity,
        BigDecimal unitPrice
) {
}
