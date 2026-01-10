package com.turkcell.cart_service.domain.repository;

import com.turkcell.cart_service.domain.model.Cart;
import com.turkcell.cart_service.domain.model.CustomerId;

import java.util.Optional;

public interface CartRepository {

    Optional<Cart> findByCustomerId(CustomerId customerId);
    Cart save(Cart cart);
    void deleteByCustomerId(CustomerId customerId);
}
