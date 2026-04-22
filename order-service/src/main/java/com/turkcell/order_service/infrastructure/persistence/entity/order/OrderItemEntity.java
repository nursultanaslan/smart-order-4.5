package com.turkcell.order_service.infrastructure.persistence.entity.order;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "order_lines")
public class OrderItemEntity {

    @Id
    @Column(columnDefinition = "uuid", nullable = false)
    private UUID productId;

    @Column(name = "product_name", nullable = false)
    private String productName;
    @Column(name = "unite_price_at_order_time")
    private BigDecimal unitPriceAtOrderTime;
    @Column(name = "currency", nullable = false)
    private String currency;
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    @Column(name = "items_total_price_At_order_time", precision = 8, scale = 2)
    private BigDecimal itemsTotalPriceAtOrderTime;

    @ManyToOne()
    @JoinColumn(name = "order_id")
    private JpaOrderEntity order;


    public OrderItemEntity(UUID productId, String productName, BigDecimal unitPriceAtOrderTime, String currency, Integer quantity, BigDecimal itemsTotalPriceAtOrderTime) {
        this.productId = productId;
        this.productName = productName;
        this.unitPriceAtOrderTime = unitPriceAtOrderTime;
        this.currency = currency;
        this.quantity = quantity;
        this.itemsTotalPriceAtOrderTime = itemsTotalPriceAtOrderTime;
    }

    public OrderItemEntity() {

    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getUnitPriceAtOrderTime() {
        return unitPriceAtOrderTime;
    }

    public void setUnitPriceAtOrderTime(BigDecimal unitPriceAtOrderTime) {
        this.unitPriceAtOrderTime = unitPriceAtOrderTime;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getItemsTotalPriceAtOrderTime() {
        return itemsTotalPriceAtOrderTime;
    }

    public void setItemsTotalPriceAtOrderTime(BigDecimal itemsTotalPriceAtOrderTime) {
        this.itemsTotalPriceAtOrderTime = itemsTotalPriceAtOrderTime;
    }

    public JpaOrderEntity getOrder() {
        return order;
    }

    public void setOrder(JpaOrderEntity order) {
        this.order = order;
    }
}
