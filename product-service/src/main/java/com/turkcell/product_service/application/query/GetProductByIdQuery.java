package com.turkcell.product_service.application.query;

import com.turkcell.product_service.application.dto.ProductResponse;
import com.turkcell.product_service.core.cqrs.Query;

import java.util.UUID;

public record GetProductByIdQuery(UUID productId) implements Query<ProductResponse> {
}
