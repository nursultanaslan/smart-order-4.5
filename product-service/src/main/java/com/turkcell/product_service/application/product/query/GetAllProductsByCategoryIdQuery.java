package com.turkcell.product_service.application.product.query;

import com.turkcell.product_service.application.product.dto.PageableProductResponse;
import com.turkcell.product_service.core.cqrs.Query;

import java.util.UUID;

public record GetAllProductsByCategoryIdQuery(
        UUID categoryId,
        Integer pageIndex,
        Integer pageSize) implements Query<PageableProductResponse> {
}
