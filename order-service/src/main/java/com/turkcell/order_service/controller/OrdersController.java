package com.turkcell.order_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.turkcell.order_service.dto.CreateOrderDto;
import com.turkcell.order_service.entity.messaging.outbox.OutboxMessage;
import com.turkcell.order_service.event.OrderCreatedEvent;
import com.turkcell.order_service.repository.OutboxRepository;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrdersController {
    //kafka ile iletişim kuruyoruz streamBridge sayesinde.
    private final StreamBridge streamBridge;
    private final OutboxRepository outboxRepository;  //mimarisel olarak bunu burada tanımlamamız dogru değil
    private final ObjectMapper objectMapper;

    public OrdersController(StreamBridge streamBridge, OutboxRepository outboxRepository, ObjectMapper objectMapper) {
        this.streamBridge = streamBridge;
        this.outboxRepository = outboxRepository;
        this.objectMapper = objectMapper;
    }

    @PostMapping
    public String createOrder(@RequestBody CreateOrderDto orderDto) throws JsonProcessingException {

        OrderCreatedEvent event = new OrderCreatedEvent(orderDto.id());

        //burası icin mapper yazılabilir
        OutboxMessage outboxMessage = new OutboxMessage();
        outboxMessage.setAggregateId(UUID.randomUUID());   //dbde oluşan order'ın id'si olacak
        outboxMessage.setAggregateType("Order");           //Order domaini
        outboxMessage.setEventId(UUID.randomUUID());
        outboxMessage.setEventType("OrderCreatedEvent");
        outboxMessage.setPayloadJson(objectMapper.writeValueAsString(event)); //gönderdiğimiz eventin(mesajın) serialize edilmiş hali

        outboxRepository.save(outboxMessage);
        /** Eski Tip mesajı direkt kafkaya gönderdiğimiz yöntem
        //Eventi message ile sarmallayıp gönderiyoruz.
        //hangi event olursa olsun Generic Message ile sarmallamak best practice'tir.
        Message<OrderCreatedEvent> message = MessageBuilder.withPayload(event).build();
        try{
            //binding ismiyle eventi alır özellikleri gönderir
            boolean isSent = streamBridge.send("orderCreated-out-0", message);
            if(!isSent){
                System.out.println("Message not sent");
            }
        }catch (Exception e){
            System.out.println("Message not sent");
        }
        **/
        return orderDto.id().toString();
    }
}
