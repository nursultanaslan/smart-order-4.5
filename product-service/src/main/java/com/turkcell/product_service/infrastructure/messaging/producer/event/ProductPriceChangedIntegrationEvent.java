package com.turkcell.product_service.infrastructure.messaging.producer.event;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductPriceChangedIntegrationEvent(
        UUID productId,
        BigDecimal amount
) {
}
