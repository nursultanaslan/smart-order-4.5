package com.turkcell.product_service.application.category.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DeleteCategoryRequest(@NotNull UUID categoryId) {
}
