package com.turkcell.product_service.application.category.dto.request;

import java.util.UUID;

public record UpdateCategoryRequest(
        UUID categoryId,
        String categoryName
) {
}
