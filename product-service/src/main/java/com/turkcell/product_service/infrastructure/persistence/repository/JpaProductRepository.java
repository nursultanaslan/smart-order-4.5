package com.turkcell.product_service.infrastructure.persistence.repository;

import com.turkcell.product_service.infrastructure.persistence.model.JpaProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaProductRepository extends JpaRepository<JpaProductEntity, UUID> {
}
