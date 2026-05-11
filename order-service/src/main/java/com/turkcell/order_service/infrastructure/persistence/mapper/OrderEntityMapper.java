package com.turkcell.order_service.infrastructure.persistence.mapper;

import com.turkcell.order_service.domain.aggregate.*;
import com.turkcell.order_service.domain.aggregate.valueobjects.*;
import com.turkcell.order_service.infrastructure.persistence.entity.order.JpaOrderEntity;
import com.turkcell.order_service.infrastructure.persistence.entity.order.OrderLineItemEntity;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class OrderEntityMapper {

    public Order toDomain(JpaOrderEntity entity) {

        List<OrderLineItem> items = entity
                .getItems()
                .stream()
                .map(this::toItemDomain)
                .toList();

        return Order.rehydrate(
                new OrderId(entity.getOrderId()),
                new CustomerId(entity.getCustomerId()),
                new CartId(entity.getCartId()),
                new Money(entity.getTotalPrice(), entity.getCurrency()),
                entity.getCreatedAt(),
                entity.getOrderStatus(),
                items
        );
    }

    public JpaOrderEntity toEntity(Order order) {

        JpaOrderEntity entity = new JpaOrderEntity();
        entity.setOrderId(order.orderId().value());
        entity.setTotalPrice(order.totalPrice().value());
        entity.setCurrency(order.totalPrice().currency());
        entity.setCreatedAt(order.createdAt());
        entity.setOrderStatus(order.orderStatus());
        entity.setCustomerId(order.customerId().value());
        entity.setCartId(order.cartId().value());

        List<OrderLineItemEntity> items = order
                .items()
                .stream()
                .map(item -> toItemEntity(item, entity))
                .toList();
        entity.setItems(items);
        return entity;
    }

    private OrderLineItem toItemDomain(OrderLineItemEntity entity) {
        return new OrderLineItem(
                entity.getProductId(),
                entity.getProductName(),
                entity.getUnitPriceAtOrderTime(),
                entity.getCurrency(),
                entity.getQuantity()
        );
    }

    private OrderLineItemEntity toItemEntity(OrderLineItem item, JpaOrderEntity entity) {
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
