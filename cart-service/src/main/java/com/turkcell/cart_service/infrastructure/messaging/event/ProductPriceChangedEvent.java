package com.turkcell.cart_service.infrastructure.messaging.event;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductPriceChangedEvent(UUID productId, BigDecimal newPrice, String currency) {
}
