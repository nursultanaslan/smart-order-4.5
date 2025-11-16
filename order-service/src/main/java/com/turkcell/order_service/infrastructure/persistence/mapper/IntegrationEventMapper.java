package com.turkcell.order_service.infrastructure.persistence.mapper;

import com.turkcell.order_service.domain.event.OrderCreatedEvent;
import com.turkcell.order_service.domain.model.OrderLine;
import com.turkcell.order_service.infrastructure.messaging.producer.event.OrderCreatedIntegrationEvent;
import com.turkcell.order_service.infrastructure.persistence.entity.OrderLineEntity;
import org.springframework.stereotype.Component;

@Component
public class IntegrationEventMapper {

    public OrderCreatedIntegrationEvent toIntegrationEvent(OrderCreatedEvent event) {
        return new OrderCreatedIntegrationEvent(
                event.orderId().value(),
                event.customerId().value(),
                event.cartId().value(),
                event.createdAt(),
                event.totalPrice(),
                event.currency(),
                event.lines()
                        .stream()
                        .map(this::toIntegrationOrderLine)
                        .toList());
    }

    private OrderLineEntity toIntegrationOrderLine(OrderLine line) {
        return new OrderLineEntity(
                line.productId(),
                line.productName(),
                line.unitPrice(),
                line.currency(),
                line.quantity(),
                line.lineTotalPrice()
        );
    }
}
