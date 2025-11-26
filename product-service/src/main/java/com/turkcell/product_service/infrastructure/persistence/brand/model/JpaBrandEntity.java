package com.turkcell.product_service.infrastructure.persistence.brand.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "brands")
public class JpaBrandEntity {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;
    @Column(name = "brand_name")
    private String brandName;

    public UUID id() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String brandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

}
