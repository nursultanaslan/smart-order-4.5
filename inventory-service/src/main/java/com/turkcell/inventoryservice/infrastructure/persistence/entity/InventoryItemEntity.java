package com.turkcell.inventoryservice.infrastructure.persistence.entity;

import jakarta.persistence.Entity;

import java.time.Instant;
import java.util.UUID;

@Entity
public class InventoryItemEntity {

    private UUID productId;

    private int reservedQuantity;
    private int availableQuantity;
    private int totalQuantity;

    private Instant lastUpdateAt;
    private Long version;

}
