package com.turkcell.cart_service.infrastructure.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@RedisHash("cart")
public class CartEntity {

    @Id
    private String cartId;

    private String customerId;
    private BigDecimal cartTotalPrice;
    private String currency;
    private OffsetDateTime createdAt;
    private CartStatus status;

    private List<CartItemEntity> items;



}
