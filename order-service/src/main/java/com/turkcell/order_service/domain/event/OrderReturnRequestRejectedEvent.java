package com.turkcell.order_service.domain.event;

import com.turkcell.order_service.domain.event.base.OrderDomainEvent;

public record OrderReturnRequestRejectedEvent() implements OrderDomainEvent {
}
