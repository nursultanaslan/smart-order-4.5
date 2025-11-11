package com.turkcell.cart_service.domain;

import java.math.BigDecimal;
import java.util.UUID;

public class Cart {

    private UUID id;
    private BigDecimal totalPrice;
    private String currency;
}
