package com.turkcell.order_service.infrastructure.persistence.repository;

import com.turkcell.order_service.infrastructure.persistence.entity.order.JpaOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaOrderRepository extends JpaRepository<JpaOrderEntity, UUID> {
}
