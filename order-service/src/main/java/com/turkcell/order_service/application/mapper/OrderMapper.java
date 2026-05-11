package com.turkcell.order_service.application.mapper;

import com.turkcell.order_service.application.command.CreateOrderCommand;
import com.turkcell.order_service.application.dto.request.OrderItemDto;
import com.turkcell.order_service.domain.aggregate.valueobjects.CartId;
import com.turkcell.order_service.domain.aggregate.valueobjects.CustomerId;
import com.turkcell.order_service.domain.aggregate.Order;
import com.turkcell.order_service.domain.aggregate.valueobjects.OrderLineItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class OrderMapper {

    public Order toDomain(CreateOrderCommand command) {
        List<OrderLineItem> orderLineItems =
                command.items().stream()
                        .map(this::toOrderItem)
                        .toList();

        return Order.create(
                new CustomerId(command.customerId()),
                new CartId(command.cartId()),
                orderLineItems);
    }

    public OrderLineItem toOrderItem(OrderItemDto dto) {
        BigDecimal itemTotalPrice = dto.unitPriceAtOrderTime()
                .multiply(BigDecimal.valueOf(dto.quantity()))
                .setScale(2, java.math.RoundingMode.HALF_UP);

        return new OrderLineItem(
                dto.productId(),
                dto.productName(),
                dto.unitPriceAtOrderTime(),
                dto.currency(),
                dto.quantity()
        );
    }
}
