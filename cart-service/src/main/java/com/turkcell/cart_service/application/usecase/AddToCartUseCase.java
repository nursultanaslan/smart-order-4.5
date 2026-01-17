package com.turkcell.cart_service.application.usecase;

import com.turkcell.cart_service.application.dto.request.CartItemRequest;
import com.turkcell.cart_service.application.dto.response.CartItemResponse;
import com.turkcell.cart_service.application.mapper.CartMapper;
import com.turkcell.cart_service.domain.model.Cart;
import com.turkcell.cart_service.domain.model.CartItem;
import com.turkcell.cart_service.domain.model.CustomerId;
import com.turkcell.cart_service.domain.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AddToCartUseCase {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    public AddToCartUseCase(CartRepository cartRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
    }

    public CartItemResponse addToCart(UUID customerId, CartItemRequest request) {

        Cart cart = cartRepository.findByCustomerId(new CustomerId(customerId))
                .orElseThrow();

        CartItem item = cartMapper.mapToCart(request);
        cart.addItemToCart(item);

        cartRepository.save(cart);
        return new CartItemResponse(
                item.quantity(),
                item.unitPrice()
        );
    }
}
