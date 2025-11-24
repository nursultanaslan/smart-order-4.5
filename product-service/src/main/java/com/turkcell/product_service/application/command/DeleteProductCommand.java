package com.turkcell.product_service.application.command;

import com.turkcell.product_service.application.dto.DeletedProductResponse;
import com.turkcell.product_service.core.cqrs.Command;

import java.util.UUID;

public record DeleteProductCommand(UUID productId) implements Command<DeletedProductResponse> {
}
