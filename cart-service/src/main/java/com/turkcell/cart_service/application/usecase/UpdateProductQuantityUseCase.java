package com.turkcell.cart_service.application.usecase;

import com.turkcell.cart_service.application.client.ProductClient;
import com.turkcell.cart_service.application.dto.response.ProductResponse;
import com.turkcell.cart_service.domain.exception.CartNotFoundException;
import com.turkcell.cart_service.domain.model.Cart;
import com.turkcell.cart_service.domain.model.CustomerId;
import com.turkcell.cart_service.domain.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateProductQuantityUseCase {

    private final CartRepository cartRepository;
    private final ProductClient productClient;

    public UpdateProductQuantityUseCase(CartRepository cartRepository, ProductClient productClient) {
        this.cartRepository = cartRepository;
        this.productClient = productClient;
    }

    public Cart updateProductQuantity(UUID customerId, UUID productId, Integer newQuantity) {

        ProductResponse product = productClient.getProductById(productId);
        Cart cart = cartRepository.findByCustomerId(new CustomerId(customerId))
                .orElseThrow(() -> new CartNotFoundException("Cart not found!"));

        cart.updateQuantity(
                productId,
                newQuantity,
                product.stock());

        Cart updatedCart = cartRepository.save(cart);
        return updatedCart;
    }
}
