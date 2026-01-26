package com.turkcell.order_service.domain.event;

import com.turkcell.order_service.domain.model.CartId;
import com.turkcell.order_service.domain.model.CustomerId;
import com.turkcell.order_service.domain.model.OrderId;
import com.turkcell.order_service.domain.model.OrderItem;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;


public record OrderCreatedEvent(
        OrderId orderId,
        CustomerId customerId,
        CartId cartId,
        OffsetDateTime createdAt,
        BigDecimal totalPrice,
        String currency,
        List<OrderItem> items) {
}
