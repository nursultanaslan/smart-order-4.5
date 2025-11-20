package com.turkcell.order_service.infrastructure.messaging.producer.adapter;

import com.turkcell.order_service.domain.event.OrderCreatedEvent;
import com.turkcell.order_service.domain.port.DomainEventsPublisher;
import com.turkcell.order_service.infrastructure.messaging.producer.event.OrderCreatedIntegrationEvent;
import com.turkcell.order_service.infrastructure.persistence.entity.outbox.OrderOutboxEntity;
import com.turkcell.order_service.infrastructure.persistence.mapper.IntegrationEventMapper;
import com.turkcell.order_service.infrastructure.persistence.mapper.OutboxMapper;
import com.turkcell.order_service.infrastructure.persistence.repository.OutboxRepository;
import org.springframework.stereotype.Component;

/**
 * Domain Events Publisher Adapter
 * Domain event'leri alıp outbox pattern kullanarak persistence katmanına kaydeder.
 * Bu sayede application layer infrastructure detaylarından bağımsız kalır.
 */

@Component
public class DomainEventsPublisherAdapter implements DomainEventsPublisher {

    private final OutboxRepository outboxRepository;
    private final OutboxMapper outboxMapper;
    private final IntegrationEventMapper integrationEventMapper;

    public DomainEventsPublisherAdapter(OutboxRepository outboxRepository, OutboxMapper outboxMapper, IntegrationEventMapper integrationEventMapper) {
        this.outboxRepository = outboxRepository;
        this.outboxMapper = outboxMapper;
        this.integrationEventMapper = integrationEventMapper;
    }

    @Override
    public void publish(OrderCreatedEvent event) {

        OrderCreatedIntegrationEvent orderIntegrationEvent =
                integrationEventMapper.toIntegrationEvent(event);

        OrderOutboxEntity orderOutboxEntity = outboxMapper.toOutbox(orderIntegrationEvent);

        outboxRepository.save(orderOutboxEntity);

    }
}
