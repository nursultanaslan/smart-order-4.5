package com.turkcell.order_service.dto;

import java.util.UUID;

public record CreateOrderDto(
        UUID id,
        String product,
        Integer quantity,
        String customerEmail
) {
}
