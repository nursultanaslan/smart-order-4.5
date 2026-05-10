package com.turkcell.order_service.infrastructure.persistence.entity.order;

import com.turkcell.order_service.domain.model.OrderStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class JpaOrderEntity {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID orderId;

    @Column(name = "total_price")
    private BigDecimal totalPrice;
    @Column(name = "currency")
    private String currency;
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "customer_id", nullable = false)
    private UUID customerId;
    @Column(name = "cart_id", nullable = false)
    private UUID cartId;

    @OneToMany(mappedBy = "order")
    private List<OrderLineItemEntity> items = new ArrayList<>();

    public JpaOrderEntity(UUID orderId, BigDecimal totalPrice, String currency, OffsetDateTime createdAt,
                          OrderStatus orderStatus, UUID customerId, UUID cartId, List<OrderLineItemEntity> items) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.currency = currency;
        this.createdAt = createdAt;
        this.orderStatus = orderStatus;
        this.customerId = customerId;
        this.cartId = cartId;
        this.items = items;
    }

    public JpaOrderEntity() {

    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public UUID getCartId() {
        return cartId;
    }

    public void setCartId(UUID cartId) {
        this.cartId = cartId;
    }

    public List<OrderLineItemEntity> getItems() {
        return items;
    }

    public void setItems(List<OrderLineItemEntity> items) {
        this.items = items;
    }
}
