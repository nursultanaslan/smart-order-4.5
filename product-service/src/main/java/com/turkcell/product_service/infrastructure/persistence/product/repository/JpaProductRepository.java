package com.turkcell.product_service.infrastructure.persistence.product.repository;

import com.turkcell.product_service.infrastructure.persistence.product.model.JpaProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaProductRepository extends JpaRepository<JpaProductEntity, UUID> {

    Page<JpaProductEntity> findByProductNameIgnoreCase(String productName, Pageable pageable);
    Page<JpaProductEntity> findByCategoryId(UUID categoryId,  Pageable pageable);
}
