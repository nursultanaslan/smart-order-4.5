package com.turkcell.order_service.application.command;

import com.turkcell.order_service.application.dto.response.OrderResponse;
import com.turkcell.order_service.core.cqrs.CommandHandler;
import com.turkcell.order_service.domain.port.OrderRepository;
import org.springframework.stereotype.Component;

@Component
public class CreateOrderCommandHandler implements CommandHandler<CreateOrderCommand, OrderResponse> {

    private final OrderRepository orderRepository;

    public CreateOrderCommandHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderResponse handle(CreateOrderCommand command) {
        return null;
    }
}
