package com.turkcell.order_service.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
/**
 * tüm güncellemeler sadece Aggregate Root üzerinden yapılır.
 * böylece Order'in iş kuralları her zaman korunur(kontrol edilip uygulandığı icin kural ihlal edilemez).
 * ve eş zamanlı güncellemeler kontrol altına alınır. version number (optimistic locking)/database lock (pessimistic locking)
 * */
//Business Object
//aggregate root : tüm iş kuralları burada yaşar
public class Order {

    private final OrderId orderId;

    private final CustomerId customerId;
    private final CartId cartId;

    private final Money totalPrice;

    private final OffsetDateTime createdAt;
    private OrderStatus orderStatus;
    private final List<OrderLineItem> items;

    private Order(OrderId orderId, CustomerId customerId, CartId cartId, Money totalPrice,
                  OffsetDateTime createdAt, OrderStatus orderStatus, List<OrderLineItem> items) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.cartId = cartId;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.orderStatus = orderStatus != null ? orderStatus : OrderStatus.getDefault();
        this.items = items;
    }

    public static Order create(CustomerId customerId, CartId cartId, List<OrderLineItem> items) {
        validateCurrencyConsistency(items);

        String currency = items.getFirst().currency();
        BigDecimal totalValue = items.stream()
                .map(OrderLineItem::calculateLineTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Money totalPrice = new Money(totalValue, currency);
        return new Order(
                OrderId.generate(),
                customerId,
                cartId,
                totalPrice,
                OffsetDateTime.now(),
                OrderStatus.getDefault(),
                items);
    }

    public static Order rehydrate(OrderId orderId, CustomerId customerId, CartId cartId, Money totalPrice,
                                  OffsetDateTime createdAt, OrderStatus orderStatus, List<OrderLineItem> items) {
        return new Order(
                orderId,
                customerId,
                cartId,
                totalPrice,
                createdAt,
                orderStatus,
                items
        );
    }

    // worker methods
    public Money calculateOrderTotalPrice() {
        BigDecimal totalValue = items.stream()
                .map(OrderLineItem::calculateLineTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        String currency = items.getFirst().currency();
        return new Money(totalValue, currency);
    }

    // set status
    //pending statusunden sonraki süreç.
    public void markInProgress() {
        this.orderStatus = OrderStatus.IN_PROGRESS;
    }

    public void markCancelled() {
        if (orderStatus != OrderStatus.PENDING) {
            throw new IllegalStateException("Only pending orders can be marked as cancelled.");
        }
        this.orderStatus = OrderStatus.CANCELLED;
    }

    //sipariş teslim edildi -> tamamlandı olarak işaretlenir
    public void markCompleted() {
        this.orderStatus = OrderStatus.COMPLETED;
    }


    public void markClaimed() {
        if (orderStatus != OrderStatus.COMPLETED) {
            throw new IllegalStateException("Sipariş teslim alındıktan sonra iptal süreci başlatılabilir.");
        }
        this.orderStatus = OrderStatus.CLAIMED;
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

    public OffsetDateTime createdAt() {
        return createdAt;
    }

    public OrderStatus orderStatus() {
        return orderStatus;
    }

    public List<OrderLineItem> items() {
        return Collections.unmodifiableList(items);
    }
}
