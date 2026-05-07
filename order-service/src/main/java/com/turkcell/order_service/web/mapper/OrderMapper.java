package com.turkcell.order_service.web.mapper;

import com.turkcell.order_service.application.command.CreateOrderCommand;
import com.turkcell.order_service.application.dto.request.OrderItemDto;
import com.turkcell.order_service.application.dto.response.CreateOrderResult;
import com.turkcell.order_service.web.dto.request.CreateOrderRequest;
import com.turkcell.order_service.web.dto.request.OrderItemRequestDto;
import com.turkcell.order_service.web.dto.response.CreateOrderResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    public CreateOrderCommand toOrderCommand(CreateOrderRequest request) {
        List<OrderItemDto> items =
                request.orderItems()
                        .stream()
                        .map(this::toOrderItemDto)
                        .toList();

        return new CreateOrderCommand(
                request.customerId(),
                request.cartId(),
                items
        );
    }

    public OrderItemDto toOrderItemDto(OrderItemRequestDto dto) {
        return new OrderItemDto(
                dto.productId(),
                dto.productName(),
                dto.unitPriceAtOrderTime(),
                dto.currency(),
                dto.quantity()
        );
    }

    public CreateOrderResponse toResponse(CreateOrderResult result) {
        return new CreateOrderResponse(
                result.orderId(),
                result.totalPrice(),
                result.currency()
        );
    }
}
