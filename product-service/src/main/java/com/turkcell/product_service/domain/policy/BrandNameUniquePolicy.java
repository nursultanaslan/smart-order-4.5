package com.turkcell.product_service.domain.policy;

public interface BrandNameUniquePolicy {
    boolean isUnique(String brandName);
}
