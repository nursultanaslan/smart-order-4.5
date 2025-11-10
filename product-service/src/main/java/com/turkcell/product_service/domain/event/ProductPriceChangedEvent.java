package com.turkcell.product_service.domain.event;

import com.turkcell.product_service.domain.model.Money;
import com.turkcell.product_service.domain.model.ProductId;

public record ProductPriceChangedEvent(ProductId id, Money newPrice) {
}
