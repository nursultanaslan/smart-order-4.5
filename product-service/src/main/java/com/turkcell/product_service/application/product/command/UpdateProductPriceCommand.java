package com.turkcell.product_service.application.product.command;

import com.turkcell.product_service.application.product.dto.ProductResponse;
import com.turkcell.product_service.core.cqrs.Command;

import java.math.BigDecimal;
import java.util.UUID;

public record UpdateProductPriceCommand(
        UUID productId,
        BigDecimal newAmount,
        String currency
) implements Command<ProductResponse> {
}
