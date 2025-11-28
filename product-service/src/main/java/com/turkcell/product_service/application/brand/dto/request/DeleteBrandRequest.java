package com.turkcell.product_service.application.brand.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DeleteBrandRequest(@NotNull UUID brandId) {
}
