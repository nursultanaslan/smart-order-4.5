package com.turkcell.order_service.domain.event;

import com.turkcell.order_service.domain.aggregate.valueobjects.CartId;
import com.turkcell.order_service.domain.aggregate.valueobjects.CustomerId;
import com.turkcell.order_service.domain.aggregate.valueobjects.OrderId;
import com.turkcell.order_service.domain.aggregate.valueobjects.OrderLineItem;

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
        List<OrderLineItem> items) {
}
