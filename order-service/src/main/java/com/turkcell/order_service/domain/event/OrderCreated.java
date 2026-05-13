package com.turkcell.order_service.domain.event;

import com.turkcell.order_service.domain.aggregate.valueobjects.*;
import com.turkcell.order_service.domain.event.base.OrderDomainEvent;

import java.time.OffsetDateTime;
import java.util.List;

public record OrderCreated(
        OrderId orderId,
        CartId cartId,
        CustomerId customerId,
        List<OrderLineItem> lineItems,
        Money totalPrice,
        OffsetDateTime createdAt
) implements OrderDomainEvent {

}
