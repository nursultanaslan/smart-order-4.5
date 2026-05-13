package com.turkcell.order_service.domain.repository;

import com.turkcell.order_service.domain.event.OrderCreated;

public interface DomainEventsPublisher {

    void publish(OrderCreated event);
}
