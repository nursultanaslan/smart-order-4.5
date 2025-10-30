package com.turkcell.product_service.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateProductRequest(
        @NotNull UUID id,
        @NotBlank @Size(min = 5) String name,
        @Positive @NotNull BigDecimal amount,
        @NotBlank String currency,
        @NotBlank String description,
        @Positive @NotNull Integer stock
) {
}
