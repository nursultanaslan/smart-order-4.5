package com.turkcell.cart_service.web.mapper;

import com.turkcell.cart_service.domain.model.Cart;
import com.turkcell.cart_service.web.dto.response.CartItemResponse;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CartMapper {

    public CartItemResponse toCartItemResponse(Cart cart, UUID productId) {
        return cart.items().stream()
                .filter(cartItem -> cartItem.productId().equals(productId))
                .findFirst()
                .map(cartItem -> new CartItemResponse(
                        cartItem.productId(),
                        cartItem.quantity(),
                        cartItem.itemTotalPrice(),
                        cartItem.currency()
                ))
                .orElseThrow();
    }
}
