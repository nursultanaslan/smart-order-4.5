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
    @Column(name = "unite_price")
    private BigDecimal unitPrice;
    @Column(name = "currency", nullable = false)
    private String currency;
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    @Column(name = "line_total_price", precision = 8, scale = 2)
    private BigDecimal itemTotalPrice;

    @ManyToOne()
    @JoinColumn(name = "order_id")
    private JpaOrderEntity order;


    public OrderItemEntity(UUID productId, String productName, BigDecimal unitPrice, String currency, Integer quantity, BigDecimal itemTotalPrice) {
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.currency = currency;
        this.quantity = quantity;
        this.itemTotalPrice = itemTotalPrice;
    }

    public OrderItemEntity() {

    }

    public UUID productId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String productName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal unitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String currency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer quantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal itemTotalPrice() {
        return itemTotalPrice;
    }

    public void setItemTotalPrice(BigDecimal itemTotalPrice) {
        this.itemTotalPrice = itemTotalPrice;
    }

    public JpaOrderEntity order() {
        return order;
    }

    public void setOrder(JpaOrderEntity order) {
        this.order = order;
    }
}
