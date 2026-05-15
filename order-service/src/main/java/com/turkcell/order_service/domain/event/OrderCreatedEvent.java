package com.turkcell.order_service.domain.event;

import com.turkcell.order_service.domain.aggregate.valueobjects.*;
import com.turkcell.order_service.domain.event.base.OrderDomainEvent;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * OrderCreatedEvent : Consumer'ların ihtiyaç duyduğu verileri içerir -> Event Enrichment
 * Bu Event Consumer'ları basitleştirir çünkü artık bu veriyi Event'i yayınlayan Service'ten istemeleri gerekmez.
 * Burada Order Aggregate'i sipariş detaylarını ekleyerek Eventleri zenginleştirir.
 * Her ne kadar Event Enrichment; Consumerları basitleştirse de dezavantajı Event classlarını daha az stable
 * hale getirebilir. Consumerların gereksinimleri değiştikçe Event classının da değişmesi gerekecektir.
 */
public record OrderCreatedEvent(
        OrderId orderId,
        CartId cartId,
        CustomerId customerId,
        List<OrderLineItem> lineItems,
        Money totalPrice,
        OffsetDateTime createdAt
) implements OrderDomainEvent {

}
