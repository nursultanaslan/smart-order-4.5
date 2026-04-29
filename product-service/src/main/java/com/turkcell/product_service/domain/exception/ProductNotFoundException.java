package com.turkcell.product_service.domain.exception;

import com.turkcell.product_service.domain.exception.base.NotFoundException;
import com.turkcell.product_service.domain.model.product.ProductId;

public class ProductNotFoundException extends NotFoundException {
    public ProductNotFoundException(ProductId productId) {
        super("Product not found with id: " + productId.value());
    }
}
