package com.turkcell.order_service.infrastructure.messaging.producer.event;


import com.turkcell.order_service.infrastructure.persistence.entity.order.OrderItemEntity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record OrderCreatedIntegrationEvent(
                UUID orderId,
                UUID customerId,
                UUID cartId,
                OffsetDateTime createdAt,
                BigDecimal totalPrice,
                String currency,
                List<OrderItemEntity> items) {
}
