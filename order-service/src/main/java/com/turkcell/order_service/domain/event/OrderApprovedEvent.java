package com.turkcell.order_service.domain.event;

import com.turkcell.order_service.domain.aggregate.valueobjects.*;
import com.turkcell.order_service.domain.event.base.OrderDomainEvent;

import java.util.List;

public record OrderApprovedEvent(
        OrderId orderId,
        OrderNumber orderNumber,
        CustomerId customerId,
        List<OrderLineItem> lineItems,
        Money totalPrice
) implements OrderDomainEvent {
}
