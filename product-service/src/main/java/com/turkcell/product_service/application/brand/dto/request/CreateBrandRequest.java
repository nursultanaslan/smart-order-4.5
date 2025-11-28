package com.turkcell.product_service.application.brand.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateBrandRequest(@NotBlank String brandName) {
}
