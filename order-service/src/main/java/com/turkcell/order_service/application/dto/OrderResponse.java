package com.turkcell.order_service.application.dto;

import java.util.UUID;

public record OrderResponse(
        UUID id,
        String product,
        Integer quantity,
        String customerEmail
) {
}
