package com.turkcell.order_service.infrastructure.persistence.entity.order;

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
    private UUID id;

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
    private List<OrderItemEntity> items = new ArrayList<>();

    public UUID id() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal totalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String currency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public OffsetDateTime createdAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OrderStatus orderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public UUID customerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public UUID cartId() {
        return cartId;
    }

    public void setCartId(UUID cartId) {
        this.cartId = cartId;
    }

    public List<OrderItemEntity> items() {
        return items;
    }

    public void setItems(List<OrderItemEntity> items) {
        this.items = items;
    }
}
