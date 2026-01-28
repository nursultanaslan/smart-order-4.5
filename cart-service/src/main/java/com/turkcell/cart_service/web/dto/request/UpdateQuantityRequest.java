package com.turkcell.cart_service.web.dto.request;

import java.util.UUID;

public record UpdateQuantityRequest(
        UUID productId,
        Integer newQuantity
) {
}
