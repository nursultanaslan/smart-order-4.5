package com.turkcell.order_service.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

public class Order {

    private final OrderId id;

    private final CustomerId customerId;
    private final CartId cartId;

    private BigDecimal totalPrice;
    private String currency;

    private OffsetDateTime createdAt;
    private OrderStatus orderStatus;
    private List<OrderLine> lines;

    private Order(OrderId id, CustomerId customerId, CartId cartId, BigDecimal totalPrice, String currency, OffsetDateTime createdAt, OrderStatus orderStatus, List<OrderLine> lines) {
        this.id = id;
        this.customerId = customerId;
        this.cartId = cartId;
        this.totalPrice = totalPrice;
        this.currency = currency;
        this.createdAt = createdAt;
        this.orderStatus = orderStatus != null ? orderStatus : OrderStatus.getDefault();
        this.lines = lines;
    }

    public static Order create(CustomerId customerId, CartId cartId, BigDecimal totalPrice, String currency, OffsetDateTime createdAt, List<OrderLine> lines){
        validateTotalPrice(totalPrice);
        return new Order(
                OrderId.generate(),
                customerId,
                cartId,
                totalPrice,
                lines.isEmpty() ? null : lines.getFirst().currency(),
                createdAt,
                OrderStatus.getDefault(),
                lines
        );
    }

    public static Order rehydrate(OrderId id, CustomerId customerId, CartId cartId, BigDecimal totalPrice, String currency, OffsetDateTime createdAt, OrderStatus orderStatus, List<OrderLine> lines){
        return new Order(
                id,
                customerId,
                cartId,
                totalPrice,
                currency,
                createdAt,
                orderStatus,
                lines
        );
    }

    //worker methods
    public void calculateTotalPrice(){
        this.totalPrice = lines
                .stream()
                .map(line -> line.unitPrice().multiply(BigDecimal.valueOf(line.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
        validateTotalPrice(this.totalPrice);

    }

    //set status
    public void markInProgress(){
        this.orderStatus = OrderStatus.IN_PROGRESS;
    }
    public void markCompleted(){
        this.orderStatus = OrderStatus.COMPLETED;
    }
    public void markCancelled(){
        if (orderStatus != OrderStatus.PENDING){
            throw new IllegalStateException("Only pending orders can be marked as cancelled.");
        }
        this.orderStatus = OrderStatus.CANCELLED;
    }
    public void markClaimed(){
        if (orderStatus != OrderStatus.COMPLETED) {
            throw new IllegalStateException("");
        }
    }

    //validate methods
    public static void validateTotalPrice(BigDecimal totalPrice){
        if(totalPrice.signum() < 0){
            throw new IllegalArgumentException("Total price cannot be negative");
        }
    }

    public static void validateCurrencyConsistency(List<OrderLine> lines){
        if (lines == null || lines.isEmpty()){
            throw new IllegalArgumentException("Order line cannot be empty");
        }

    }

    //getters
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

    public List<OrderLine> lines() {
        return Collections.unmodifiableList(lines);
    }
}
