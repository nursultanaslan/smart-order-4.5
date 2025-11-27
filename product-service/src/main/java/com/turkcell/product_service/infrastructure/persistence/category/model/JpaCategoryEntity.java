package com.turkcell.product_service.infrastructure.persistence.category.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "categories")
public class JpaCategoryEntity {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name = "category_name")
    private String categoryName;

    public JpaCategoryEntity() {
    }

    public JpaCategoryEntity(UUID id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    public UUID id() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String categoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
