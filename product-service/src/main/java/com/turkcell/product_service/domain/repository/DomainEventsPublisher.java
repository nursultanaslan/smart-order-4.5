package com.turkcell.product_service.domain.repository;

import com.turkcell.product_service.domain.event.ProductPriceChangedEvent;

public interface DomainEventsPublisher {

    void publish(ProductPriceChangedEvent event);
}
