package com.turkcell.product_service.domain.policy;

public interface CategoryNameUniquePolicy {
    boolean isUnique(String categoryName);
}
