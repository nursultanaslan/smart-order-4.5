package com.turkcell.cart_service.infrastructure;

import com.turkcell.cart_service.domain.model.Cart;
import com.turkcell.cart_service.domain.model.CustomerId;
import com.turkcell.cart_service.domain.repository.CartRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RedisCartRepositoryAdapter implements CartRepository {

    @Override
    public Optional<Cart> findByCustomerId(CustomerId customerId) {
        return Optional.empty();
    }

    @Override
    public Cart save(Cart cart) {
        return null;
    }

    @Override
    public void deleteByCustomerId(CustomerId customerId) {

    }
}
