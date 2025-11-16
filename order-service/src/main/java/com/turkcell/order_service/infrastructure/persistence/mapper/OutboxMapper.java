package com.turkcell.order_service.infrastructure.persistence.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.turkcell.order_service.infrastructure.messaging.producer.event.OrderCreatedIntegrationEvent;
import com.turkcell.order_service.infrastructure.persistence.entity.OutboxMessage;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OutboxMapper {

    private final ObjectMapper objectMapper;

    public OutboxMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public OutboxMessage toOutbox(OrderCreatedIntegrationEvent integrationEvent) {
        OutboxMessage outboxMessage = new OutboxMessage();
        outboxMessage.setAggregateId(integrationEvent.orderId());
        outboxMessage.setAggregateType("Order");
        outboxMessage.setEventId(UUID.randomUUID());
        outboxMessage.setEventType("OrderCreatedEvent");
        outboxMessage.setPayloadJson(serializeEvent(integrationEvent));
        return outboxMessage;
    }

    // from java object(integrationEvent) to serialize as json
    // serialize anında hata çıkabilir bu yüzden writeValueAsString methodu
    // JsonProcessingException
    // hatası fırlatır ve bunu handle etmeni bekler.
    public String serializeEvent(OrderCreatedIntegrationEvent integrationEvent) {
        String eventJson;
        try{
            eventJson = objectMapper.writeValueAsString(integrationEvent);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("could not serialize integrationEvent", e);
        }
        return eventJson;
    }
}
