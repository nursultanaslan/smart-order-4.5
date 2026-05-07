package com.turkcell.order_service.web.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public class CreateOrderResponse {

    private UUID orderId;
    private BigDecimal totalPrice;
    private String currency;

    public CreateOrderResponse(UUID orderId, BigDecimal totalPrice, String currency) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.currency = currency;
    }

    public CreateOrderResponse() {
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
