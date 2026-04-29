package com.turkcell.product_service.infrastructure.persistence.brand.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "brands")
public class JpaBrandEntity {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID brandId;
    @Column(name = "brand_name")
    private String brandName;

    public JpaBrandEntity() {
    }

    public JpaBrandEntity(UUID brandId, String brandName) {
        this.brandId = brandId;
        this.brandName = brandName;
    }

    public UUID getBrandId() {
        return brandId;
    }

    public void setBrandId(UUID brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
