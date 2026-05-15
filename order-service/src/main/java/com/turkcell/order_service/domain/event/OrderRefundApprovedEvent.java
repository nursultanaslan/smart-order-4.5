package com.turkcell.order_service.domain.event;

import com.turkcell.order_service.domain.aggregate.valueobjects.OrderId;
import com.turkcell.order_service.domain.event.base.OrderDomainEvent;

public record OrderRefundApprovedEvent(
        OrderId orderId
) implements OrderDomainEvent {
}
