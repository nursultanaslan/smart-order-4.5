package com.turkcell.order_service.controller;

import com.turkcell.order_service.dto.CreateOrderDto;
import com.turkcell.order_service.event.OrderCreatedEvent;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class OrdersController {
    //kafka ile iletişim kuruyoruz streamBridge sayesinde.
    private final StreamBridge streamBridge;

    public OrdersController(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @PostMapping
    public String createOrder(@RequestBody CreateOrderDto orderDto) {
        OrderCreatedEvent event = new OrderCreatedEvent(orderDto.id());
        //Eventi message ile sarmallayıp gönderiyoruz.
        //hangi event olursa olsun Generic Message ile sarmallamak best practice'tir.
        Message<OrderCreatedEvent> message = MessageBuilder.withPayload(event).build();
        //binding ismiyle eventi alır özellikleri gönderir
        streamBridge.send("orderCreated-out-0", message);
        return orderDto.id().toString();
    }
}
