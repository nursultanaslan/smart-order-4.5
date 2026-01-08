package com.turkcell.product_service.infrastructure.persistence.outbox.repository;

import com.turkcell.product_service.infrastructure.persistence.outbox.ProductOutboxEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OutboxRepository extends JpaRepository<ProductOutboxEntity, UUID> {
}
