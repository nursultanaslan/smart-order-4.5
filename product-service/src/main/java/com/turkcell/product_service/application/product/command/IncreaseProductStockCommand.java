package com.turkcell.product_service.application.product.command;

import com.turkcell.product_service.application.product.dto.ProductResponse;
import com.turkcell.product_service.core.cqrs.Command;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record IncreaseProductStockCommand(
        @NotNull UUID productId,
        @Positive @NotNull Integer quantity) implements Command<ProductResponse> {
}
