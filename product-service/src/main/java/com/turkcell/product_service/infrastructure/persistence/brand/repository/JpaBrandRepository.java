package com.turkcell.product_service.infrastructure.persistence.brand.repository;

import com.turkcell.product_service.infrastructure.persistence.brand.model.JpaBrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaBrandRepository extends JpaRepository<JpaBrandEntity, UUID> {
}
