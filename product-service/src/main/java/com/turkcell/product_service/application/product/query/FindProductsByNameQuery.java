package com.turkcell.product_service.application.product.query;

import com.turkcell.product_service.application.product.dto.PageableProductResponse;
import com.turkcell.product_service.core.cqrs.Query;

public record FindProductsByNameQuery(
        String productName,
        Integer pageIndex,
        Integer pageSize) implements Query<PageableProductResponse> {
}
