package com.turkcell.product_service.application.brand.dto.request;

import java.util.UUID;

public record UpdateBrandRequest(
        UUID brandId,
        String brandName
) {
}
