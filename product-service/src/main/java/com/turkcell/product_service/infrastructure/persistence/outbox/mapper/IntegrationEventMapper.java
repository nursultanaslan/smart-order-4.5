package com.turkcell.product_service.infrastructure.persistence.outbox.mapper;

import com.turkcell.product_service.domain.event.ProductPriceChangedEvent;
import com.turkcell.product_service.infrastructure.messaging.producer.event.ProductPriceChangedIntegrationEvent;
import org.springframework.stereotype.Component;

@Component
public class IntegrationEventMapper {

    public ProductPriceChangedIntegrationEvent toIntegrationEvent(ProductPriceChangedEvent event) {
        return new ProductPriceChangedIntegrationEvent(
                event.id().value(),
                event.newPrice().amount()
        );
    }
}
