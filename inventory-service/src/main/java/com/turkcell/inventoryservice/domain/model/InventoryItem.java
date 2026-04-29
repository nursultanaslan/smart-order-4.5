package com.turkcell.inventoryservice.domain.model;

import java.time.Instant;
import java.util.UUID;

public class InventoryItem {
    private UUID productId;
    private Integer availableQuantity;
    private Integer reservedQuantity;
    private Integer totalQuantity;
    private Instant lastUpdatedAt;


}
