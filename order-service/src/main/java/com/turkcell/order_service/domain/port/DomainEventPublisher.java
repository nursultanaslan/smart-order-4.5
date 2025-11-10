package com.turkcell.order_service.domain.port;

import com.turkcell.order_service.domain.event.OrderCreatedEvent;

public interface DomainEventPublisher {

    void publish(OrderCreatedEvent event);
}
