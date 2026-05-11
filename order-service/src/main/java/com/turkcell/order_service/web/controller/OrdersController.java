package com.turkcell.order_service.web.controller;

import com.turkcell.order_service.application.command.CreateOrderCommand;
import com.turkcell.order_service.application.dto.response.CreateOrderResult;
import com.turkcell.order_service.core.cqrs.CommandHandler;
import com.turkcell.order_service.web.dto.request.CreateOrderRequest;
import com.turkcell.order_service.web.dto.response.CreateOrderResponse;
import com.turkcell.order_service.web.mapper.OrderMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * INBOUND ADAPTER
 */
@RestController
@RequestMapping("/api/v1/orders")
@Validated
public class OrdersController {

    private final CommandHandler<CreateOrderCommand, CreateOrderResult> createOrderCommandHandler;
    private final OrderMapper orderMapper;

    public OrdersController(CommandHandler<CreateOrderCommand, CreateOrderResult> createOrderCommandHandler, OrderMapper orderMapper) {
        this.createOrderCommandHandler = createOrderCommandHandler;
        this.orderMapper = orderMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateOrderResponse createOrder(@RequestBody @Valid CreateOrderRequest request) {
        CreateOrderCommand command = orderMapper.toOrderCommand(request);
        CreateOrderResult result = createOrderCommandHandler.handle(command);
        return orderMapper.toResponse(result);
    }
}
