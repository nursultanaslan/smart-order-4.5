package com.turkcell.product_service.domain.event;

import com.turkcell.product_service.domain.model.product.ProductId;

public record ProductStockDecreasedEvent(ProductId id, Integer stock) {
}
