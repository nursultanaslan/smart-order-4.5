package com.turkcell.product_service.application.product.dto;

import java.math.BigDecimal;

public record GetProductByIdResponse(
        String productName,
        BigDecimal amount,
        String currency,
        String description,
        Integer stock
) {
}
