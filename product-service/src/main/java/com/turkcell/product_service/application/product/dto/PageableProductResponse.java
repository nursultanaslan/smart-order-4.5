package com.turkcell.product_service.application.product.dto;

import java.util.List;

public record PageableProductResponse(
        List<ProductDto> products) {
}
