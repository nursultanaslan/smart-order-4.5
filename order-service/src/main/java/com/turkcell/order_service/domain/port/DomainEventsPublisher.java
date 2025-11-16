package com.turkcell.order_service.domain.port;

import com.turkcell.order_service.domain.event.OrderCreatedEvent;

public interface DomainEventsPublisher {

    void publish(OrderCreatedEvent event);
}
