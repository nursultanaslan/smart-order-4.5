package com.turkcell.product_service.application.product.query;

import com.turkcell.product_service.application.product.dto.GetProductByIdResponse;
import com.turkcell.product_service.application.product.dto.ProductResponse;
import com.turkcell.product_service.core.cqrs.Query;

import java.util.UUID;

public record GetProductByIdQuery(UUID productId) implements Query<GetProductByIdResponse> {
}
