package com.turkcell.cart_service.infrastructure.persistence.repository;

import com.turkcell.cart_service.domain.model.Cart;
import com.turkcell.cart_service.domain.model.CustomerId;
import com.turkcell.cart_service.domain.repository.CartRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
public class RedisCartRepositoryAdapter implements CartRepository {

    private final RedisTemplate<String, Cart> redisTemplate;

    public RedisCartRepositoryAdapter(RedisTemplate<String, Cart> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Cart save(Cart cart) {
        String key = "cart:" + cart.customerId().value().toString();
        redisTemplate.opsForValue().set(key, cart, 7, TimeUnit.DAYS);
        return cart;
    }

    @Override
    public Optional<Cart> findByCustomerId(CustomerId customerId) {
        String key = "cart:" + customerId.value().toString();
        Cart cart = redisTemplate.opsForValue().get(key);
        return Optional.ofNullable(cart);
    }

    @Override
    public void deleteByCustomerId(CustomerId customerId) {
        redisTemplate.delete("cart:" + customerId.value().toString());
    }
}
