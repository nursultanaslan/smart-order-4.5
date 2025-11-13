package com.turkcell.order_service.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.turkcell.order_service.application.dto.response.OrderResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class OrdersController {

    @PostMapping
    public OrderResponse createOrder(@RequestBody OrderResponse orderDto) throws JsonProcessingException {
        return null;
    }
}
