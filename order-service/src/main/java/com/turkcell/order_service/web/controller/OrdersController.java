package com.turkcell.order_service.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.turkcell.order_service.application.dto.response.OrderResponse;
import com.turkcell.order_service.infrastructure.persistence.entity.messaging.outbox.OutboxMessage;
import com.turkcell.order_service.domain.event.OrderCreatedEvent;
import com.turkcell.order_service.infrastructure.persistence.repository.OutboxRepository;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrdersController {

    @PostMapping
    public OrderResponse createOrder(@RequestBody OrderResponse orderDto) throws JsonProcessingException {
        return null;
    }
}
