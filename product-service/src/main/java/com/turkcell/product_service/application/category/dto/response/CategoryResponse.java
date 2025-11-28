package com.turkcell.product_service.application.category.dto.response;

import java.util.UUID;

public record CategoryResponse(UUID categoryId, String categoryName) {
}
