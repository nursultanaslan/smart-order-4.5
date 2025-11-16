package com.turkcell.order_service.application.command;

import com.turkcell.order_service.application.dto.response.OrderResponse;
import com.turkcell.order_service.application.mapper.OrderMapper;
import com.turkcell.order_service.core.cqrs.CommandHandler;
import com.turkcell.order_service.domain.event.OrderCreatedEvent;
import com.turkcell.order_service.domain.model.Order;
import com.turkcell.order_service.domain.port.DomainEventsPublisher;
import com.turkcell.order_service.domain.port.OrderRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CreateOrderCommandHandler implements CommandHandler<CreateOrderCommand, OrderResponse> {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final DomainEventsPublisher domainEventsPublisher;

    public CreateOrderCommandHandler(OrderRepository orderRepository, OrderMapper orderMapper, DomainEventsPublisher domainEventsPublisher) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.domainEventsPublisher = domainEventsPublisher;
    }

    @Override
    @Transactional
    public OrderResponse handle(CreateOrderCommand command) {

        Order order = orderMapper.toDomain(command);

        Order savedOrder = orderRepository.save(order);

        OrderCreatedEvent event = new OrderCreatedEvent(
                order.id(),
                order.customerId(),
                order.cartId(),
                order.createdAt(),
                order.totalPrice(),
                order.currency(),
                order.lines()
        );

        domainEventsPublisher.publish(event);

        return orderMapper.toResponse(savedOrder);
    }
}
