package com.turkcell.product_service.infrastructure.messaging.contract;

import java.util.UUID;

public record OrderCreatedEvent(UUID id) {
}
