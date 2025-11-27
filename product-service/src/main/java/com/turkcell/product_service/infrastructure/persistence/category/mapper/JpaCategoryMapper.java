package com.turkcell.product_service.infrastructure.persistence.category.mapper;

import com.turkcell.product_service.domain.model.category.Category;
import com.turkcell.product_service.domain.model.category.CategoryId;
import com.turkcell.product_service.infrastructure.persistence.category.model.JpaCategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class JpaCategoryMapper {

    public JpaCategoryEntity toEntity(Category category) {
        return new JpaCategoryEntity(
                category.id().value(),
                category.categoryName()
        );
    }

    public Category toDomain(JpaCategoryEntity entity) {
        return Category.rehydrate(
                new CategoryId(entity.id()),
                entity.categoryName()
        );
    }
}
