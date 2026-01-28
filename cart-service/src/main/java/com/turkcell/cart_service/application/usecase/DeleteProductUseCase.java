package com.turkcell.cart_service.application.usecase;

import com.turkcell.cart_service.domain.exception.CartNotFoundException;
import com.turkcell.cart_service.domain.model.Cart;
import com.turkcell.cart_service.domain.model.CustomerId;
import com.turkcell.cart_service.domain.repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Service
@Validated
public class DeleteProductUseCase {

    private final CartRepository cartRepository;

    public DeleteProductUseCase(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart deleteCartItem(UUID customerId, UUID productId) {

        Cart cart = cartRepository.findByCustomerId(new CustomerId(customerId))
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));

        cart.removeItemFromCart(productId);
        cartRepository.save(cart);


        return cart;
    }
}
