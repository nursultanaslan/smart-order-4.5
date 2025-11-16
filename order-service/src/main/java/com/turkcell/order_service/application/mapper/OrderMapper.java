package com.turkcell.order_service.application.mapper;

import com.turkcell.order_service.application.command.CreateOrderCommand;
import com.turkcell.order_service.application.dto.request.OrderLineDto;
import com.turkcell.order_service.application.dto.response.OrderResponse;
import com.turkcell.order_service.domain.model.CartId;
import com.turkcell.order_service.domain.model.CustomerId;
import com.turkcell.order_service.domain.model.Order;
import com.turkcell.order_service.domain.model.OrderLine;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public Order toDomain(CreateOrderCommand command) {
        List<OrderLine> orderLines = command.lines().stream()
                .map(this::toOrderLine)
                .collect(Collectors.toList());

        return Order.create(
                new CustomerId(command.customerId()),
                new CartId(command.cartId()),
                OffsetDateTime.now(),
                orderLines);
    }

    public OrderLine toOrderLine(OrderLineDto dto) {
        BigDecimal lineTotalPrice = dto.price()
                .multiply(BigDecimal.valueOf(dto.quantity()))
                .setScale(2, java.math.RoundingMode.HALF_UP);

        return new OrderLine(
                dto.productId(),
                dto.productName(),
                dto.price(),
                dto.currency(),
                dto.quantity(),
                lineTotalPrice);
    }

    public OrderLineDto toDto(OrderLine orderLine) {
        return new OrderLineDto(
                orderLine.productId(),
                orderLine.productName(),
                orderLine.quantity(),
                orderLine.unitPrice(),
                orderLine.currency());
    }

    public OrderResponse toResponse(Order order) {
        return new OrderResponse(
                order.id().value(),
                order.totalPrice(),
                order.currency());
    }
}
