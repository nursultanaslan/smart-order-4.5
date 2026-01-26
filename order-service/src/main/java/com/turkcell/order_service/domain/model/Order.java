package com.turkcell.order_service.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

//aggregate-root
public class Order {

    private final OrderId id;

    private final CustomerId customerId;
    private final CartId cartId;

    private BigDecimal totalPrice;
    private final String currency;

    private final OffsetDateTime createdAt;
    private OrderStatus orderStatus;
    private final List<OrderItem> items;

    private Order(OrderId id, CustomerId customerId, CartId cartId, BigDecimal totalPrice, String currency,
            OffsetDateTime createdAt, OrderStatus orderStatus, List<OrderItem> items) {
        this.id = id;
        this.customerId = customerId;
        this.cartId = cartId;
        this.totalPrice = totalPrice;
        this.currency = currency;
        this.createdAt = createdAt;
        this.orderStatus = orderStatus != null ? orderStatus : OrderStatus.getDefault();
        this.items = items;
    }

    public static Order create(CustomerId customerId, CartId cartId, OffsetDateTime createdAt, List<OrderItem> items) {
        validateCurrencyConsistency(items);
        Order order = new Order(
                OrderId.generate(),
                customerId,
                cartId,
                null, // totalPrice will be calculated
                items.getFirst().currency(), // currency from first item (all items have same currency after validation)
                createdAt,
                OrderStatus.getDefault(),
                items);
        order.calculateTotalPrice();
        return order;
    }

    public static Order rehydrate(OrderId id, CustomerId customerId, CartId cartId, BigDecimal totalPrice,
            String currency, OffsetDateTime createdAt, OrderStatus orderStatus, List<OrderItem> items) {
        return new Order(
                id,
                customerId,
                cartId,
                totalPrice,
                currency,
                createdAt,
                orderStatus,
                items
        );
    }

    // worker methods
    public void calculateTotalPrice() {
        this.totalPrice = items
                .stream()
                .map(item -> item.unitPrice().multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
        validateTotalPrice(this.totalPrice);

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
    public static void validateTotalPrice(BigDecimal totalPrice) {
        if (totalPrice.signum() < 0) {
            throw new IllegalArgumentException("Total price cannot be negative");
        }
    }

    public static void validateCurrencyConsistency(List<OrderItem> items) {
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
    public OrderId id() {
        return id;
    }

    public CustomerId customerId() {
        return customerId;
    }

    public CartId cartId() {
        return cartId;
    }

    public BigDecimal totalPrice() {
        return totalPrice;
    }

    public String currency() {
        return currency;
    }

    public OffsetDateTime createdAt() {
        return createdAt;
    }

    public OrderStatus orderStatus() {
        return orderStatus;
    }

    public List<OrderItem> items() {
        return Collections.unmodifiableList(items);
    }
}
