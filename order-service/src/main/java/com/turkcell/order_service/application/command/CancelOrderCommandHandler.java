package com.turkcell.order_service.application.command;

import com.turkcell.order_service.application.dto.response.OrderResponse;
import com.turkcell.order_service.core.cqrs.CommandHandler;

public class CancelOrderCommandHandler implements CommandHandler<CancelOrderCommand, OrderResponse> {

    @Override
    public OrderResponse handle(CancelOrderCommand command) {
        return null;
    }
}
