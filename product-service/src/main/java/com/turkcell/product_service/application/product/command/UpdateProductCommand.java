package com.turkcell.product_service.application.product.command;

import com.turkcell.product_service.application.product.dto.ProductResponse;
import com.turkcell.product_service.core.cqrs.Command;

import java.math.BigDecimal;
import java.util.UUID;

public record UpdateProductCommand(
        UUID productId,
        String productName,
        BigDecimal amount,
        String currency,
        String description,
        Integer stock
) implements Command<ProductResponse> {
}
