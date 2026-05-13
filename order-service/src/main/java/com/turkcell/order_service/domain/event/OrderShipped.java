package com.turkcell.order_service.domain.event;

import com.turkcell.order_service.domain.aggregate.OrderStatus;
import com.turkcell.order_service.domain.aggregate.valueobjects.Money;
import com.turkcell.order_service.domain.aggregate.valueobjects.OrderId;
import com.turkcell.order_service.domain.aggregate.valueobjects.OrderLineItem;
import com.turkcell.order_service.domain.event.base.OrderDomainEvent;

import java.util.List;

public record OrderShipped(
        OrderId orderId,
        OrderStatus status,
        List<OrderLineItem> lineItems,
        Money totalPrice
) implements OrderDomainEvent {
}
