package com.turkcell.product_service.infrastructure.messaging.producer.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.turkcell.product_service.domain.event.ProductPriceChangedEvent;
import com.turkcell.product_service.domain.repository.DomainEventsPublisher;
import com.turkcell.product_service.infrastructure.messaging.producer.event.ProductPriceChangedIntegrationEvent;
import com.turkcell.product_service.infrastructure.persistence.outbox.ProductOutboxEntity;
import com.turkcell.product_service.infrastructure.persistence.outbox.mapper.IntegrationEventMapper;
import com.turkcell.product_service.infrastructure.persistence.outbox.repository.OutboxRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DomainEventsPublisherAdapter implements DomainEventsPublisher {

    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;
    private final IntegrationEventMapper integrationEventMapper;

    public DomainEventsPublisherAdapter(OutboxRepository outboxRepository, ObjectMapper objectMapper, IntegrationEventMapper integrationEventMapper) {
        this.outboxRepository = outboxRepository;
        this.objectMapper = objectMapper;
        this.integrationEventMapper = integrationEventMapper;
    }

    @Override
    public void publish(ProductPriceChangedEvent event) {

        ProductPriceChangedIntegrationEvent integrationEvent = integrationEventMapper.toIntegrationEvent(event);

        ProductOutboxEntity productOutboxEntity = new ProductOutboxEntity();
        productOutboxEntity.setAggregateId(integrationEvent.productId());
        productOutboxEntity.setAggregateType("Product");
        productOutboxEntity.setEventId(UUID.randomUUID());
        productOutboxEntity.setEventType("ProductPriceChangedEvent");
        productOutboxEntity.setPayloadJson(serializeEvent(integrationEvent));


        outboxRepository.save(productOutboxEntity);
    }

    //TODO: Generic olmalı
    public String serializeEvent(ProductPriceChangedIntegrationEvent integrationEvent) {
        String eventJson;
        try {
            eventJson = objectMapper.writeValueAsString(integrationEvent);
        }catch (JsonProcessingException e) {
            throw new RuntimeException("Could not serialize event to JSON");
        }
        return eventJson;
    }
}
