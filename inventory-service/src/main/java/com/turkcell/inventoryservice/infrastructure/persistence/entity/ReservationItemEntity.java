package com.turkcell.inventoryservice.infrastructure.persistence.entity;

import jakarta.persistence.Entity;

import java.util.UUID;

@Entity
public class ReservationItemEntity {

    private UUID productId;
    private Integer quantity;
}
