package com.turkcell.order_service.application.mapper;

import com.turkcell.order_service.application.command.CreateOrderCommand;
import com.turkcell.order_service.application.dto.request.OrderLineDto;
import com.turkcell.order_service.domain.model.CartId;
import com.turkcell.order_service.domain.model.CustomerId;
import com.turkcell.order_service.domain.model.Order;
import com.turkcell.order_service.domain.model.OrderLine;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class OrderMapper {

    public OrderLineDto toDto(OrderLine orderLine) {
        return new OrderLineDto(
                orderLine.productId(),
                orderLine.quantity(),
                orderLine.unitPrice()
        );
    }

//    public Order toDomain(CreateOrderCommand command) {
//        return Order.create(
//                new CustomerId(command.customerId()),
//                new CartId(command.cartId()),
//                OffsetDateTime.now(),
//
//        )
//
//    }
}
