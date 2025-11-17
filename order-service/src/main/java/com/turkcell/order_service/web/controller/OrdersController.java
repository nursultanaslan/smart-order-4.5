package com.turkcell.order_service.web.controller;

import com.turkcell.order_service.application.command.CreateOrderCommand;
import com.turkcell.order_service.application.dto.response.OrderResponse;
import com.turkcell.order_service.core.cqrs.CommandHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrdersController {

    private final CommandHandler<CreateOrderCommand, OrderResponse> createOrderCommandHandler;

    public OrdersController(CommandHandler<CreateOrderCommand, OrderResponse> createOrderCommandHandler) {
        this.createOrderCommandHandler = createOrderCommandHandler;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@RequestBody CreateOrderCommand command){
        return createOrderCommandHandler.handle(command);
    }
}
