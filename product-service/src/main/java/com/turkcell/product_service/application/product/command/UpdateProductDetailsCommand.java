package com.turkcell.product_service.application.product.command;

import com.turkcell.product_service.application.product.dto.ProductResponse;
import com.turkcell.product_service.core.cqrs.Command;

import java.util.UUID;

public record UpdateProductDetailsCommand(
        UUID productId,
        String productName,
        String description,
        Integer stock
) implements Command<ProductResponse> {
}
