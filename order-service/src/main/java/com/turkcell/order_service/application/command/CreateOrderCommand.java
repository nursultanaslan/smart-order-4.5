package com.turkcell.order_service.application.command;

import com.turkcell.order_service.application.dto.request.OrderLineDto;
import com.turkcell.order_service.application.dto.response.OrderResponse;
import com.turkcell.order_service.core.cqrs.Command;

import java.util.List;
import java.util.UUID;

public record CreateOrderCommand(
        UUID customerId,
        UUID cartId,
        List<OrderLineDto> lines) implements Command<OrderResponse> {
}
