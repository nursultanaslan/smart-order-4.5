package com.turkcell.order_service.infrastructure.messaging.producer.adapter;

import com.turkcell.order_service.domain.event.base.DomainEvent;
import com.turkcell.order_service.domain.repository.OrderDomainEventPublisher;
import com.turkcell.order_service.infrastructure.persistence.mapper.IntegrationEventMapper;
import com.turkcell.order_service.infrastructure.persistence.mapper.OutboxMapper;
import com.turkcell.order_service.infrastructure.persistence.repository.OutboxRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Domain Events Publisher Adapter (OUTBOUND ADAPTER)
 * Domain event'leri alıp outbox pattern kullanarak persistence katmanına kaydeder.
 * Bu sayede application layer infrastructure detaylarından bağımsız kalır.
 */

@Component
public class OrderDomainEventPublishingAdapter implements OrderDomainEventPublisher {

    private final OutboxRepository outboxRepository;
    private final OutboxMapper outboxMapper;
    private final IntegrationEventMapper integrationEventMapper;

    public OrderDomainEventPublishingAdapter(OutboxRepository outboxRepository, OutboxMapper outboxMapper, IntegrationEventMapper integrationEventMapper) {
        this.outboxRepository = outboxRepository;
        this.outboxMapper = outboxMapper;
        this.integrationEventMapper = integrationEventMapper;
    }

    @Override
    public void publish(List<DomainEvent> domainEvents) {

    }
}
