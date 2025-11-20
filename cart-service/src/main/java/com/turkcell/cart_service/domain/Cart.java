package com.turkcell.cart_service.domain;

import java.math.BigDecimal;

public class Cart {

    private final CartId id;

    private BigDecimal totalPrice;
    private String currency;

    private Cart(CartId id, BigDecimal totalPrice, String currency) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.currency = currency;
    }

    public static Cart create(CartId id, BigDecimal totalPrice, String currency) {
        return new Cart(
                CartId.generate(),
                totalPrice,
                currency
        );
    }

    public static Cart rehydrate(CartId id, BigDecimal totalPrice, String currency) {
        return new Cart(id, totalPrice, currency);
    }

}
