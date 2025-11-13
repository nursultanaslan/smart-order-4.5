package com.turkcell.order_service.infrastructure.persistence.repository;

import org.hibernate.query.criteria.JpaOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaOrderRepository extends JpaRepository<JpaOrder, UUID> {
}
