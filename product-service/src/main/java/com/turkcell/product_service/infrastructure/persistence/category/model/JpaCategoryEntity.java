package com.turkcell.product_service.infrastructure.persistence.category.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "categories")
public class JpaCategoryEntity {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID categoryId;

    @Column(name = "category_name")
    private String categoryName;

    public JpaCategoryEntity() {
    }

    public JpaCategoryEntity(UUID categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
