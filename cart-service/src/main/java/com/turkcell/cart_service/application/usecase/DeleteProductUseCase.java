package com.turkcell.cart_service.application.usecase;

import com.turkcell.cart_service.domain.model.CustomerId;
import com.turkcell.cart_service.domain.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteProductUseCase {

    private final CartRepository cartRepository;

    public DeleteProductUseCase(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public void deleteCartItem(UUID customerId, UUID productId) {

        cartRepository.findByCustomerId(new CustomerId(customerId)).ifPresent(
                cart ->{
                    cart.removeItemFromCart(productId);
                    cartRepository.save(cart);
                }
        );
    }
}
