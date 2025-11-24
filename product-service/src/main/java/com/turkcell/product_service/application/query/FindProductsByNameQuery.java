package com.turkcell.product_service.application.query;

import com.turkcell.product_service.application.dto.PageableProductResponse;
import com.turkcell.product_service.core.cqrs.Query;

public record FindProductsByNameQuery(
        String productName,
        Integer pageIndex,
        Integer pageSize) implements Query<PageableProductResponse> {
}
