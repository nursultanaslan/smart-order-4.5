package com.turkcell.inventoryservice.infrastructure.persistence.entity;

import com.turkcell.inventoryservice.domain.model.ReservationStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
public class ReservationEntity {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID reservationId;
    private UUID orderId;

    private List<ReservationItemEntity> items;
    private ReservationStatus status;

    private Instant createdAt;
    private Instant expiresAt;
    private Long version;

}
