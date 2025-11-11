package com.turkcell.order_service.application.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderLineDto(UUID productId, Integer quantity, BigDecimal price) {
}
