package com.turkcell.order_service.domain.service;

import com.turkcell.order_service.domain.aggregate.valueobjects.CartId;
import com.turkcell.order_service.domain.aggregate.valueobjects.CustomerId;
import com.turkcell.order_service.domain.aggregate.valueobjects.OrderLineItem;
import com.turkcell.order_service.domain.repository.OrderDomainEventPublisher;
import com.turkcell.order_service.domain.repository.OrderRepository;

import java.util.List;

public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDomainEventPublisher domainEventPublisher;

    public OrderService(OrderRepository orderRepository, OrderDomainEventPublisher domainEventPublisher) {
        this.orderRepository = orderRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    public void createOrder(CustomerId customerId, CartId cartId, List<OrderLineItem> items) {

    }

    public void cancelOrder() {

    }

}
