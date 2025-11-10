package com.turkcell.order_service.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public class Order {

    private final OrderId id;
    private UUID customerId;
    private BigDecimal totalPrice;
    private String currency;
    private OffsetDateTime createdAt;
    private String  orderStatus;
    private List<OrderLine> lines;

    private Order(OrderId id) {
        this.id = id;
    }
}
