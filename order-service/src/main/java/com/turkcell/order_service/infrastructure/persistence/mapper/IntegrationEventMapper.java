package com.turkcell.order_service.infrastructure.persistence.mapper;

import com.turkcell.order_service.domain.event.OrderCreatedEvent;
import com.turkcell.order_service.domain.model.OrderItem;
import com.turkcell.order_service.infrastructure.messaging.producer.event.OrderCreatedIntegrationEvent;
import com.turkcell.order_service.infrastructure.persistence.entity.order.OrderItemEntity;
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
                event.items()
                        .stream()
                        .map(this::toIntegrationOrderItem)
                        .toList());
    }

    private OrderItemEntity toIntegrationOrderItem(OrderItem item) {
        return new OrderItemEntity(
                item.productId(),
                item.productName(),
                item.unitPrice(),
                item.currency(),
                item.quantity(),
                item.itemTotalPrice()
        );
    }
}
