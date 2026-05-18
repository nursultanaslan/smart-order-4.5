package com.turkcell.order_service.domain.aggregate;

import com.turkcell.order_service.domain.aggregate.valueobjects.*;
import com.turkcell.order_service.domain.event.OrderCreatedEvent;
import com.turkcell.order_service.domain.event.base.ResultWithDomainEvents;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

/**
 * tüm güncellemeler sadece Aggregate Root üzerinden yapılır.
 * böylece Order'in iş kuralları her zaman korunur(kontrol edilip uygulandığı icin kural ihlal edilemez).
 * ve eş zamanlı güncellemeler kontrol altına alınır. version number (optimistic locking)/database lock (pessimistic locking)
 */
//Business Object : sadece business rules bilir.
//aggregate root : tüm iş kuralları burada yaşar
public class Order {

    private final OrderId orderId;

    private final CustomerId customerId;
    private final CartId cartId;

    private final Money totalPrice;

    private OrderStatus orderStatus;
    private final List<OrderLineItem> items;

    private final OffsetDateTime createdAt;
    private final OffsetDateTime deliveredAt;
    private final OffsetDateTime cancelledAt;
    private final OffsetDateTime updatedAt;

    private Long version;

    private Order(OrderId orderId, CustomerId customerId, CartId cartId, Money totalPrice,
                  List<OrderLineItem> items, OffsetDateTime createdAt, OffsetDateTime deliveredAt,
                  OffsetDateTime cancelledAt, OffsetDateTime updatedAt, Long version) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.cartId = cartId;
        this.totalPrice = totalPrice;
        this.orderStatus = OrderStatus.getDefault();
        this.items = items;
        this.createdAt = createdAt;
        this.deliveredAt = deliveredAt;
        this.cancelledAt = cancelledAt;
        this.updatedAt = updatedAt;
        this.version = version;
    }

    //CreateOrder Saga'sını başlatır.
    public static ResultWithDomainEvents<Order, OrderCreatedEvent>
            create(CustomerId customerId, CartId cartId, List<OrderLineItem> items) {
        validateCurrencyConsistency(items);

        String currency = items.getFirst().currency();
        BigDecimal totalValue = items.stream()
                .map(OrderLineItem::calculateLineTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Money totalPrice = new Money(totalValue, currency);
        Order order = new Order(
                OrderId.generate(),
                customerId,
                cartId,
                totalPrice,
                items,
                OffsetDateTime.now(),
                null,
                null,
                null,
                0L
        );

        OrderCreatedEvent event = new OrderCreatedEvent(
                order.orderId,
                cartId,
                customerId,
                items,
                totalPrice,
                OffsetDateTime.now()

        );

        return new ResultWithDomainEvents<>(order, event);
    }

    public static Order rehydrate(OrderId orderId, CustomerId customerId, CartId cartId, Money totalPrice,
                                  List<OrderLineItem> items, OffsetDateTime createdAt, OffsetDateTime deliveredAt,
                                  OffsetDateTime cancelledAt, OffsetDateTime updatedAt, Long version) {
        return new Order(
                orderId,
                customerId,
                cartId,
                totalPrice,
                items,
                createdAt,
                deliveredAt,
                cancelledAt,
                updatedAt,
                version
        );
    }

    // domain behaviors/worker methods/business logic

    public void confirm() {
    }

    public void preparing() {

    }

    public void shipped() {

    }

    public void cancel() {

    }

    public Money getOrderTotal(){
        BigDecimal totalPrice = items.stream()
                .map(OrderLineItem::calculateLineTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        String currency = items.getFirst().currency();
        return new Money(totalPrice, currency);
    }

    // validate methods - invariants
    public static void validateCurrencyConsistency(List<OrderLineItem> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Order items cannot be null or empty");
        }

        String firstCurrency = items.getFirst().currency();
        boolean allSameCurrency = items.stream()
                .allMatch(item -> firstCurrency.equals(item.currency()));

        if (!allSameCurrency) {
            throw new IllegalArgumentException("All order items must have the same currency");
        }
    }

    // getters
    public OrderId orderId() {
        return orderId;
    }

    public CustomerId customerId() {
        return customerId;
    }

    public CartId cartId() {
        return cartId;
    }

    public Money totalPrice() {
        return totalPrice;
    }

    public OrderStatus orderStatus() {
        return orderStatus;
    }

    public List<OrderLineItem> items() {
        return Collections.unmodifiableList(items);
    }

    public OffsetDateTime createdAt() {
        return createdAt;
    }

    public OffsetDateTime deliveredAt() {
        return deliveredAt;
    }

    public OffsetDateTime cancelledAt() {
        return cancelledAt;
    }

    public OffsetDateTime updatedAt() {
        return updatedAt;
    }

    public Long version() {
        return version;
    }
}
