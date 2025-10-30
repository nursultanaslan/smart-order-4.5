package com.turkcell.order_service.controller;

import com.turkcell.order_service.dto.CreateOrderDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class OrdersController {

    @PostMapping
    public String createOrder(@RequestBody CreateOrderDto orderDto) {

    }
}
