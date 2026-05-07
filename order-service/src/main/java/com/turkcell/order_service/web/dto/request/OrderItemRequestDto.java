package com.turkcell.order_service.web.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemRequestDto(
        @NotNull
        UUID productId,
        @NotBlank
        @Size(min = 3, max = 120)
        String productName,
        @Positive
        BigDecimal unitPriceAtOrderTime,
        @Pattern(regexp = "^[A-Z]{3}$")
        @NotBlank
        String currency,
        @Positive
        Integer quantity
) {

}
