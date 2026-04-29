package com.turkcell.product_service.domain.exception;

import com.turkcell.product_service.domain.exception.base.NotFoundException;
import com.turkcell.product_service.domain.model.brand.BrandId;

public class BrandNotFoundException extends NotFoundException {
    public BrandNotFoundException(BrandId brandId) {
        super("Brand not found with id: " + brandId.value());
    }
}
