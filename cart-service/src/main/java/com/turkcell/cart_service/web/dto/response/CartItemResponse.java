package com.turkcell.cart_service.web.dto.response;

import java.util.UUID;

public record CartItemResponse(
        UUID productId,
        Integer quantity
) {
}
