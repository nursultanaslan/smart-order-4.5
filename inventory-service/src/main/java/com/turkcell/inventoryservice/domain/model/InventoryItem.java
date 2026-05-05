package com.turkcell.inventoryservice.domain.model;

import java.time.Instant;
/**
 * Domain model representing a product's inventory stock levels.
 * Pure business logic with NO framework dependencies.
 * Invariant: totalQuantity = availableQuantity + reservedQuantity (ALWAYS)
 */
public class InventoryItem {

    private final ProductId productId;

    private int reservedQuantity;  //reservedStock
    private int availableQuantity; //availableStock
    private int totalQuantity;     //totalStock

    private Instant lastUpdatedAt;
    private Long version;


    private InventoryItem(ProductId productId, Integer availableQuantity, Integer reservedQuantity, Integer totalQuantity, Instant lastUpdatedAt, Long version) {
        this.productId = productId;
        this.availableQuantity = availableQuantity;
        this.reservedQuantity = reservedQuantity;
        this.totalQuantity = totalQuantity;
        this.lastUpdatedAt = lastUpdatedAt;
        this.version = version;
    }


    //getters
    public ProductId productId() {
        return productId;
    }

    public Integer reservedQuantity() {
        return reservedQuantity;
    }

    public Integer availableQuantity() {
        return availableQuantity;
    }

    public Integer totalQuantity() {
        return totalQuantity;
    }

    public Instant lastUpdatedAt() {
        return lastUpdatedAt;
    }

    public Long version() {
        return version;
    }
}
