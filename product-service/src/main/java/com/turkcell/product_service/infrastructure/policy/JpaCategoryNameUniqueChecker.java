package com.turkcell.product_service.infrastructure.policy;

import com.turkcell.product_service.domain.policy.CategoryNameUniquePolicy;
import com.turkcell.product_service.domain.repository.CategoryRepository;
import org.springframework.stereotype.Component;

@Component
public class JpaCategoryNameUniqueChecker implements CategoryNameUniquePolicy {
    private final CategoryRepository categoryRepository;

    public JpaCategoryNameUniqueChecker(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public boolean isUnique(String categoryName) {
        return !categoryRepository.existsByCategoryNameIgnoreCase(categoryName);
    }
}
