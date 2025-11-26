package com.turkcell.product_service.domain.event;

import com.turkcell.product_service.domain.model.product.Money;
import com.turkcell.product_service.domain.model.product.ProductId;

public record ProductPriceChangedEvent(ProductId id, Money newPrice) {
}
