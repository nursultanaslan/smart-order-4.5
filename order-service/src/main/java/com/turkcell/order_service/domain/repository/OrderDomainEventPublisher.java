package com.turkcell.order_service.domain.repository;

import com.turkcell.order_service.domain.event.base.DomainEvent;

import java.util.List;

public interface OrderDomainEventPublisher {

    void publish(List<DomainEvent> domainEvents);
}
