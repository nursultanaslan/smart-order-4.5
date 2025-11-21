package com.turkcell.order_service.application.command;

import com.turkcell.order_service.application.dto.response.OrderResponse;
import com.turkcell.order_service.core.cqrs.Command;

import java.util.UUID;

public record CancelOrderCommand(UUID orderId) implements Command<OrderResponse> {
}
