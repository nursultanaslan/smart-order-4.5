package com.turkcell.order_service.infrastructure.persistence.mapper;

import com.turkcell.order_service.domain.event.OrderCreated;
import com.turkcell.order_service.domain.aggregate.valueobjects.OrderLineItem;
import com.turkcell.order_service.infrastructure.messaging.producer.event.OrderCreatedIntegrationEvent;
import com.turkcell.order_service.infrastructure.persistence.entity.order.JpaOrderEntity;
import com.turkcell.order_service.infrastructure.persistence.entity.order.OrderLineItemEntity;
import org.springframework.stereotype.Component;

@Component
public class IntegrationEventMapper {

    public OrderCreatedIntegrationEvent toIntegrationEvent(OrderCreated event) {
        return new OrderCreatedIntegrationEvent();
    }

    private OrderLineItemEntity toIntegrationOrderItem(OrderLineItem item, JpaOrderEntity entity) {
        return new OrderLineItemEntity(
                item.productId(),
                item.productName(),
                item.unitPriceAtOrderTime(),
                item.currency(),
                item.quantity(),
                entity
        );
    }
}
