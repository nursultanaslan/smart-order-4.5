package com.turkcell.order_service.application.command;

import com.turkcell.order_service.application.dto.request.OrderItemDto;
import com.turkcell.order_service.application.dto.response.OrderResponse;
import com.turkcell.order_service.core.cqrs.Command;

import java.util.List;
import java.util.UUID;

public record CreateOrderCommand(
        UUID customerId,
        UUID cartId,
        List<OrderItemDto> lines) implements Command<OrderResponse> {
}
