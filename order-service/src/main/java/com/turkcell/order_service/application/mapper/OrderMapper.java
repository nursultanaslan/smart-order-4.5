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
import java.util.List;

@Component
public class OrderMapper {

    public Order toDomain(CreateOrderCommand command) {
        List<OrderItem> orderItems =
                command.items().stream()
                        .map(this::toOrderItem)
                        .toList();

        return Order.create(
                new CustomerId(command.customerId()),
                new CartId(command.cartId()),
                orderItems);
    }

    public OrderItem toOrderItem(OrderItemDto dto) {
        BigDecimal itemTotalPrice = dto.unitPriceAtOrderTime()
                .multiply(BigDecimal.valueOf(dto.quantity()))
                .setScale(2, java.math.RoundingMode.HALF_UP);

        return new OrderItem(
                dto.productId(),
                dto.productName(),
                dto.unitPriceAtOrderTime(),
                dto.currency(),
                dto.quantity(),
                dto.lineTotalPrice()
        );
    }

    public OrderItemDto toDto(OrderItem orderItem) {
        return new OrderItemDto(
                orderItem.productId(),
                orderItem.productName(),
                orderItem.unitPriceAtOrderTime(),
                orderItem.currency(),
                orderItem.quantity(),
                orderItem.lineTotalPrice()
        );
    }

    public OrderResponse toResponse(Order order) {
        return new OrderResponse(
                order.id().value(),
                order.totalPrice().value(),
                order.totalPrice().currency());
    }
}
