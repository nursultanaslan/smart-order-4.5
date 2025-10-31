package com.turkcell.order_service.repository;

import com.turkcell.order_service.entity.messaging.outbox.OutboxMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
//Outbox üzerinden işlem yapcagız (okuyacagız)
public interface OutboxRepository extends JpaRepository<OutboxMessage, UUID> {
}
