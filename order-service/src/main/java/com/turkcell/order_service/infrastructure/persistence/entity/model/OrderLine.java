package com.turkcell.order_service.infrastructure.persistence.entity.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "order_lines")
public class OrderLine {

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
    private BigDecimal lineTotalPrice;

    @ManyToOne()
    @JoinColumn(name = "order_id")
    private Order order;

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

    public BigDecimal lineTotalPrice() {
        return lineTotalPrice;
    }

    public void setLineTotalPrice(BigDecimal lineTotalPrice) {
        this.lineTotalPrice = lineTotalPrice;
    }

    public Order order() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
