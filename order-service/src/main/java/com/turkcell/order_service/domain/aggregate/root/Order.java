package com.turkcell.order_service.domain.aggregate.root;

import com.turkcell.order_service.domain.aggregate.enums.OrderStatus;
import com.turkcell.order_service.domain.aggregate.valueobjects.*;
import com.turkcell.order_service.domain.event.OrderCreatedEvent;
import com.turkcell.order_service.domain.event.base.ResultWithDomainEvents;
import com.turkcell.order_service.domain.exception.ReturnPeriodExpiredException;
import com.turkcell.order_service.domain.exception.UnsupportedStateTransitionException;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Collections;
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
    private Instant deliveredAt;
    private Instant cancelledAt;
    private Instant updatedAt;

    private Long version;

    private Order(OrderId orderId, OrderNumber orderNumber, CustomerId customerId, CartId cartId,
                  Money totalPrice, List<OrderLineItem> items, Instant createdAt, Instant deliveredAt,
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

    public static Order rehydrate(OrderId orderId, OrderNumber orderNumber, CustomerId customerId, CartId cartId,
                                  Money totalPrice, List<OrderLineItem> items, Instant createdAt, Instant deliveredAt,
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

    //sipariş hazırlanıyor. ödeme onaylandıktan (payment confirmed) sonra çağrılır.
    public void preparing() {
        if (orderStatus == OrderStatus.APPROVED) {
            this.orderStatus = OrderStatus.PREPARING;
        } else {
            throw new UnsupportedStateTransitionException(orderStatus);
        }
        this.updatedAt = Instant.now();
    }

    //sipariş kargoya verildi.
    //ödeme alınmıştır -> sipariş APPROVED olmuştur -> hazırlanmıştır -> kargoya verilebilir.
    public void shipped() {
        if (orderStatus == OrderStatus.PREPARING) {
            this.orderStatus = OrderStatus.SHIPPED;
        } else  {
            throw new UnsupportedStateTransitionException(orderStatus);
        }
        this.updatedAt = Instant.now();
    }

    //sipariş iptal edildi -> stok geri eklenir -> ödeme iade süreci tetiklenir
    //sipariş oluşturulduktan sonra 15 dakika içerisinde iptal edilebilir.
    //siparişi oluşturdun ve bir anda karar degiştirdin -> iptal etmek istedin -> 15 dkn var.
    //siparişi oluşturduktan sonra 15 dk geçmiş ise artık iptal edilemez.
    public void cancel() {
        if (orderStatus == OrderStatus.APPROVED) {
            this.orderStatus = OrderStatus.CANCEL_PENDING;
        }else {
            throw new UnsupportedStateTransitionException(orderStatus);
        }
        //createdAt kontrolu, duration check
        Instant cancelDeadline = createdAt.plus(Duration.ofMinutes(15));
        if( Instant.now() != cancelDeadline ) {
            throw new IllegalArgumentException("iptal etmek için süre doldu!");
        }
        this.updatedAt = Instant.now();
    }

    //sipariş teslim edilmiştir. -> iade süresi dolmustur -> herhangi bir iade yapılmamıstır
    // 14 günün sonunda order COMPLETED olarak işaretlenir.
    //TODO: nasıl tetiklenecek & herhangi bir noktayı tetiklemesi gerekecek mi?
    public void complete() {
        if (Instant.now().isBefore(deliveredAt.plus(Duration.ofDays(14)))) {
            throw new IllegalArgumentException("iade süresi doldu!");
        }
        if (orderStatus != OrderStatus.DELIVERED) {
            throw new IllegalArgumentException("teslim edilmemiş bir sipariş tamamlandı olarak işaretlenemez");
        }
        this.orderStatus = OrderStatus.COMPLETED;
        this.updatedAt = Instant.now();
    }

    //shipment service'ten gelen eventle tetiklenir ve order DELIVERED olarak işaretlenir.
    public void delivered() {
        if (orderStatus == OrderStatus.SHIPPED) {
            this.orderStatus = OrderStatus.DELIVERED;
        }
        this.updatedAt = Instant.now();
    }

    //TODO:Event fırlat.
    //sipariş iptal edildi.
    public void noteCancelled() {
        if (orderStatus == OrderStatus.CANCEL_PENDING) {
            this.orderStatus = OrderStatus.CANCELLED;
        }else  {
            throw new UnsupportedStateTransitionException(orderStatus);
        }
        this.cancelledAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    //TODO:Event fırlat.
    //sipariş onaylandı.
    public void noteApproved() {
        if (orderStatus == OrderStatus.APPROVAL_PENDING) {
            this.orderStatus = OrderStatus.APPROVED;
        }else   {
            throw new UnsupportedStateTransitionException(orderStatus);
        }
        this.updatedAt = Instant.now();
    }

    //TODO:Event fırlat.
    //sipariş reddedildi.
    public void noteRejected() {
        if (orderStatus == OrderStatus.APPROVAL_PENDING) {
            this.orderStatus = OrderStatus.REJECTED;
        } else  {
            throw new UnsupportedStateTransitionException(orderStatus);
        }
        this.updatedAt = Instant.now();
    }

    //TODO:Event fırlat.
    //iade kabul edildi -> geri ödeme (refund) yapıldı.
    public void noteReturned(){
        if (orderStatus == OrderStatus.RETURN_REQUESTED) {
            this.orderStatus = OrderStatus.RETURNED;
        }else   {
            throw new UnsupportedStateTransitionException(orderStatus);
        }
        this.updatedAt = Instant.now();
    }

    //siparişi iade et.
    // sipariş teslim edildi -> deliveredAt tarihinden sonra 14 gün içerisinde
    //
    public void returnOrder(){
        if(orderStatus == OrderStatus.DELIVERED) {
            this.orderStatus = OrderStatus.RETURN_REQUESTED;
        }
        Instant returnDeadline = deliveredAt.plus(Duration.ofDays(14));
        if (Instant.now().isAfter(returnDeadline)) {
            throw new ReturnPeriodExpiredException("iade süresi doldu.");
        }
        this.orderStatus = OrderStatus.RETURN_REQUESTED;
        this.updatedAt = Instant.now();
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
    public OrderId getOrderId() {
        return orderId;
    }

    public OrderNumber getOrderNumber() {
        return orderNumber;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public CartId getCartId() {
        return cartId;
    }

    public Money getTotalPrice() {
        return totalPrice;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<OrderLineItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getDeliveredAt() {
        return deliveredAt;
    }

    public Instant getCancelledAt() {
        return cancelledAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Long getVersion() {
        return version;
    }
}
