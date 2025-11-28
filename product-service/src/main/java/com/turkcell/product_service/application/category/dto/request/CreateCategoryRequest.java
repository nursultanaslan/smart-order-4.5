package com.turkcell.product_service.application.category.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryRequest(@NotBlank String categoryName) {
}
