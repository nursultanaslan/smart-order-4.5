package com.turkcell.cart_service.application.mapper;

import com.turkcell.cart_service.application.dto.request.CartItemRequest;
import com.turkcell.cart_service.domain.model.CartItem;
import org.springframework.stereotype.Component;

@Component
public class CartMapper {

    public CartItem mapToCart(CartItemRequest cartItemRequest) {

        return new CartItem(
                cartItemRequest.productId(),
                cartItemRequest.quantity(),
                cartItemRequest.unitPrice()
        );
    }
}
