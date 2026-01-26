package com.turkcell.cart_service.web.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record CartItemResponse(
        UUID productId,
        Integer quantity,
        BigDecimal itemTotalPrice,
        String currency
) {
}
