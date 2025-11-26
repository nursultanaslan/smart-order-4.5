package com.turkcell.product_service.infrastructure.persistence.product.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "products")
public class JpaProductEntity {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;
    @Column(name = "product_name", nullable = false)
    private String productName;
    @Column(name = "amount", nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;
    @Column(name = "currency", nullable = false)
    private String currency;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "brand_id",  nullable = false)
    private UUID brandId;
    @Column(name = "category_id",  nullable = false)
    private UUID categoryId;

    public JpaProductEntity() {
    }

    public JpaProductEntity(UUID id, String productName, BigDecimal amount, String currency, String description, Integer stock, UUID brandId, UUID categoryId) {
        this.id = id;
        this.productName = productName;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.stock = stock;
        this.brandId = brandId;
        this.categoryId = categoryId;
    }

    public UUID id() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String productName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal amount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String currency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String description() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer stock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public UUID brandId() {
        return brandId;
    }

    public void setBrandId(UUID brandId) {
        this.brandId = brandId;
    }

    public UUID categoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }
}
