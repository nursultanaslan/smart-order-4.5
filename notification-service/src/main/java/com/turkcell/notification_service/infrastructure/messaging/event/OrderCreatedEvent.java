package com.turkcell.notification_service.infrastructure.messaging.event;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record OrderCreatedEvent(
                UUID orderId,
                UUID customerId,
                UUID cartId,
                OffsetDateTime createdAt,
                BigDecimal totalPrice,
                String currency,
                List<OrderLineDto> lines) {
}
