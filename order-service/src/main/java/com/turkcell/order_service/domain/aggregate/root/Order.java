package com.turkcell.order_service.domain.aggregate.root;

import com.turkcell.order_service.domain.aggregate.enums.OrderStatus;
import com.turkcell.order_service.domain.aggregate.valueobjects.*;
import com.turkcell.order_service.domain.event.OrderCreatedEvent;
import com.turkcell.order_service.domain.event.base.ResultWithDomainEvents;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * tüm güncellemeler sadece Aggregate Root üzerinden yapılır.
 * böylece Order'in iş kuralları her zaman korunur(kontrol edilip uygulandığı icin kural ihlal edilemez).
 * ve eş zamanlı güncellemeler kontrol altına alınır. version number (optimistic locking)/database lock (pessimistic locking)
 */
//Business Object : sadece business rules bilir.
//Aggregate Root : tüm iş kuralları burada yaşar.
public class Order {

    private final OrderId orderId;          //technical identifier
    private final OrderNumber orderNumber;  //business identifier

    private final CustomerId customerId;
    private final CartId cartId;

    private final Money totalPrice;

    private OrderStatus orderStatus;
    private final List<OrderLineItem> items;

    private final Instant createdAt;
    private final Instant deliveredAt;
    private final Instant cancelledAt;
    private Instant updatedAt;

    private Long version;

    private Order(OrderId orderId, OrderNumber orderNumber, CustomerId customerId, CartId cartId, Money totalPrice,
                  List<OrderLineItem> items, Instant createdAt, Instant deliveredAt,
                  Instant cancelledAt, Instant updatedAt, Long version) {
        this.orderId = orderId;
        this.orderNumber = orderNumber;
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

    //Create Order Saga'sını başlatır. (factory method)
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
                OrderNumber.generate(),
                customerId,
                cartId,
                totalPrice,
                items,
                Instant.now(),
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

    public static Order rehydrate(OrderId orderId, OrderNumber orderNumber, CustomerId customerId, CartId cartId, Money totalPrice,
                                  List<OrderLineItem> items, Instant createdAt, Instant deliveredAt,
                                  Instant cancelledAt, Instant updatedAt, Long version) {
        return new Order(
                orderId,
                orderNumber,
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
    //sipariş hazırlanıyor
    public void preparing() {
        if (orderStatus == OrderStatus.APPROVED) {
            this.orderStatus = OrderStatus.PREPARING;
        } else {
            throw new IllegalArgumentException("Order status cannot be preparing");
        }
        this.updatedAt = Instant.now();
    }

    //sipariş kargoya verildi.
    public void shipped() {


    }

    //sipariş iptal edildi.
    //sipariş oluşturulduktan sonra 15 dakika içerisinde iptal edilebilir.
    //siparişi oluşturdun ve bir anda karar degiştirdin -> iptal etmek istedin -> 15 dkn var.
    //siparişi oluşturduktan sonra 15 dk geçmiş ise artık iptal edilemez.
    public void cancel() {
        if (orderStatus == OrderStatus.APPROVED) {
            this.orderStatus = OrderStatus.CANCEL_PENDING;
        }else {
            //TODO: UnsupportedStateTransitionException
            throw new IllegalArgumentException("Order status cannot be cancelled");
        }
    }

    public void undoPendingCancel() {
        if (orderStatus == OrderStatus.CANCEL_PENDING) {
            this.orderStatus = OrderStatus.APPROVED;
        }else  {
            throw new IllegalArgumentException("Order status cannot be undo pending cancel");
        }
    }

    public void noteCancelled() {
        if (orderStatus == OrderStatus.CANCEL_PENDING) {
            this.orderStatus = OrderStatus.CANCELLED;
        }else  {
            throw new IllegalArgumentException("Order status cannot be cancelled");
        }
    }

    public void noteApproved() {
        if (orderStatus == OrderStatus.APPROVAL_PENDING) {
            this.orderStatus = OrderStatus.APPROVED;
        }else   {
            throw new IllegalArgumentException("Order status cannot be approved");
        }
    }

    public void noteRejected() {
        if (orderStatus == OrderStatus.APPROVAL_PENDING) {
            this.orderStatus = OrderStatus.REJECTED;
        } else  {
            throw new IllegalArgumentException("Order status cannot be rejected");
        }
    }

    //siparişi iade et. sipariş teslim edildi -> deliveredAt tarihinden sonra 14 gün içerisinde
    public void returnOrder(){

    }

    public void calcReturnDeadline() {

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

    public OrderNumber orderNumber() {
        return orderNumber;
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
        return items;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant deliveredAt() {
        return deliveredAt;
    }

    public Instant cancelledAt() {
        return cancelledAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    public Long version() {
        return version;
    }
}
