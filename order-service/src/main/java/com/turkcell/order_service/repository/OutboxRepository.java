package com.turkcell.order_service.repository;

import com.turkcell.order_service.entity.messaging.outbox.OutboxMessage;
import com.turkcell.order_service.entity.messaging.outbox.OutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;
//Outbox üzerinden işlem yapcagız (okuyacagız)
public interface OutboxRepository extends JpaRepository<OutboxMessage, UUID> {

    //sana verilen outbox statusu al createdAt'e göre azalan sırala
    List<OutboxMessage> findByStatusOrderByCreatedAtAsc(OutboxStatus status);
}
