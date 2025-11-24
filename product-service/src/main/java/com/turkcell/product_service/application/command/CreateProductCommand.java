package com.turkcell.product_service.application.command;

import com.turkcell.product_service.application.dto.ProductResponse;
import com.turkcell.product_service.core.cqrs.Command;

import java.math.BigDecimal;

public record CreateProductCommand(
        String name,
        BigDecimal amount,
        String currency,
        String description,
        Integer stock
) implements Command<ProductResponse> {
}
