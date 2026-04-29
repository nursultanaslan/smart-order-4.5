package com.turkcell.product_service.infrastructure.persistence.category.repository;

import com.turkcell.product_service.domain.model.category.CategoryId;
import com.turkcell.product_service.infrastructure.persistence.category.model.JpaCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaCategoryRepository extends JpaRepository<JpaCategoryEntity, UUID> {

    boolean existsByCategoryNameIgnoreCase(String categoryName);
    boolean existsByCategoryId(UUID categoryId);
}
