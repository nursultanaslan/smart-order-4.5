package com.turkcell.order_service.application.mapper;

import com.turkcell.order_service.application.command.CreateOrderCommand;
import com.turkcell.order_service.application.dto.request.OrderItemDto;
import com.turkcell.order_service.application.dto.response.OrderResponse;
import com.turkcell.order_service.domain.model.CartId;
import com.turkcell.order_service.domain.model.CustomerId;
import com.turkcell.order_service.domain.model.Order;
import com.turkcell.order_service.domain.model.OrderItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public Order toDomain(CreateOrderCommand command) {
        List<OrderItem> orderItems =
                command.items().stream()
                .map(this::toOrderItem)
                .collect(Collectors.toList());

        return Order.create(
                new CustomerId(command.customerId()),
                new CartId(command.cartId()),
                OffsetDateTime.now(),
                orderItems);
    }

    public OrderItem toOrderItem(OrderItemDto dto) {
        BigDecimal itemTotalPrice = dto.price()
                .multiply(BigDecimal.valueOf(dto.quantity()))
                .setScale(2, java.math.RoundingMode.HALF_UP);

        return new OrderItem(
                dto.productId(),
                dto.productName(),
                dto.price(),
                dto.currency(),
                dto.quantity(),
                itemTotalPrice);
    }

    public OrderItemDto toDto(OrderItem orderItem) {
        return new OrderItemDto(
                orderItem.productId(),
                orderItem.productName(),
                orderItem.quantity(),
                orderItem.unitPrice(),
                orderItem.currency());
    }

    public OrderResponse toResponse(Order order) {
        return new OrderResponse(
                order.id().value(),
                order.totalPrice(),
                order.currency());
    }
}
