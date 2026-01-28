package com.turkcell.cart_service.web.dto.response;

import com.turkcell.cart_service.domain.model.CartItem;

import java.math.BigDecimal;
import java.util.List;

public record CartResponse(
        BigDecimal cartTotalPrice,
        List<CartItem> items
) {
}
