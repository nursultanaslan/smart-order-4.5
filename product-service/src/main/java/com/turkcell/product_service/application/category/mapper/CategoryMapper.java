package com.turkcell.product_service.application.category.mapper;

import com.turkcell.product_service.application.category.dto.request.CreateCategoryRequest;
import com.turkcell.product_service.application.category.dto.response.CategoryResponse;
import com.turkcell.product_service.application.category.dto.response.DeletedCategoryResponse;
import com.turkcell.product_service.domain.model.category.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toDomain(CreateCategoryRequest request) {
        return Category.create(
                request.categoryName()
        );
    }

    public CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
                category.id().value(),
                category.categoryName()
        );
    }

    public DeletedCategoryResponse toDeletedResponse(Category category) {
        return new DeletedCategoryResponse(
                category.id().value()
        );
    }
}
