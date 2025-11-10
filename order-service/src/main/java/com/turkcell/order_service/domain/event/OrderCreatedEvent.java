package com.turkcell.order_service.domain.event;

import java.util.UUID;

public record OrderCreatedEvent(UUID id) {
}
