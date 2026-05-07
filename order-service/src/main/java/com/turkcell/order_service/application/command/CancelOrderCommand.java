package com.turkcell.order_service.application.command;

import com.turkcell.order_service.core.cqrs.Command;

import java.util.UUID;

public record CancelOrderCommand(UUID orderId) implements Command<UUID> {
}
