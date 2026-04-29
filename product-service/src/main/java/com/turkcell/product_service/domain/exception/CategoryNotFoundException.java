package com.turkcell.product_service.domain.exception;

import com.turkcell.product_service.domain.exception.base.NotFoundException;
import com.turkcell.product_service.domain.model.category.CategoryId;

public class CategoryNotFoundException extends NotFoundException {
    public CategoryNotFoundException(CategoryId categoryId) {
        super("Category not found with id: " + categoryId.value());
    }
}
