package com.turkcell.customer_service.persistence.repository;

import com.turkcell.customer_service.persistence.entity.JpaCustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaCustomerRepository extends JpaRepository<JpaCustomerEntity, UUID> {
}
