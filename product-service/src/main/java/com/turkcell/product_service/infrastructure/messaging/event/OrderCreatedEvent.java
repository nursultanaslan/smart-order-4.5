package com.turkcell.product_service.infrastructure.messaging.event;

import java.util.UUID;

public record OrderCreatedEvent(UUID id) {
}
