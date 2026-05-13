package com.turkcell.order_service.domain.event;

import com.turkcell.order_service.domain.aggregate.valueobjects.CartId;
import com.turkcell.order_service.domain.aggregate.valueobjects.CustomerId;
import com.turkcell.order_service.domain.aggregate.valueobjects.OrderLineItem;
import com.turkcell.order_service.domain.event.base.OrderDomainEvent;

import java.math.BigDecimal;
import java.util.List;

public class OrderCreated implements OrderDomainEvent {

    private List<OrderLineItem> lineItems;
    private CartId cartId;
    private CustomerId customerId;
    private BigDecimal totalPrice;
    private String currency;

}
