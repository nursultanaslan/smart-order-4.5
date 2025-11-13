package com.turkcell.order_service.domain.event;

import com.turkcell.order_service.domain.model.OrderStatus;
import com.turkcell.order_service.domain.model.CustomerId;
import com.turkcell.order_service.domain.model.OrderId;

import java.math.BigDecimal;
import java.time.OffsetDateTime;


public record OrderCreatedEvent(
        OrderId id,
        CustomerId customerId,
        OffsetDateTime createdAt,
        BigDecimal totalPrice,
        String currency,
        OrderStatus status) {
}
