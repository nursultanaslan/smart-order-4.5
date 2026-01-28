package com.turkcell.cart_service.application.usecase;

import com.turkcell.cart_service.domain.exception.CartNotFoundException;
import com.turkcell.cart_service.domain.model.Cart;
import com.turkcell.cart_service.domain.model.CustomerId;
import com.turkcell.cart_service.domain.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetCartCustomerByIdUseCase {

    private final CartRepository cartRepository;

    public GetCartCustomerByIdUseCase(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart getCart(UUID customerId) {
        return cartRepository.findByCustomerId(new CustomerId(customerId))
                .orElseThrow(() -> new CartNotFoundException("Cart not found!"));
    }
}
