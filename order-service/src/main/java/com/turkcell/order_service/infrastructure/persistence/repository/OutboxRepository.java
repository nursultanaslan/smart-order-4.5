package com.turkcell.order_service.infrastructure.persistence.repository;

import com.turkcell.order_service.infrastructure.persistence.entity.OrderOutboxEntity;
import com.turkcell.order_service.infrastructure.persistence.entity.OrderOutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;
//Outbox üzerinden işlem yapcagız (okuyacagız)
public interface OutboxRepository extends JpaRepository<OrderOutboxEntity, UUID> {

    //sana verilen outbox statusu al createdAt'e göre azalan sırala
    List<OrderOutboxEntity> findByStatusOrderByCreatedAtAsc(OrderOutboxStatus status);
}
