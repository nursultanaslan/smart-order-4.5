package com.turkcell.cart_service.infrastructure.repository;

import com.turkcell.cart_service.domain.model.Cart;
import com.turkcell.cart_service.domain.model.CustomerId;
import com.turkcell.cart_service.infrastructure.entity.CartEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CrudCartRepository extends CrudRepository<CartEntity, String> {

    Optional<Cart> findByCustomerId(String customerId);
}
