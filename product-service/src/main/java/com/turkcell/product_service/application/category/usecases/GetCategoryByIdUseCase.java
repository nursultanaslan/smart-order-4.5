package com.turkcell.product_service.application.category.usecases;

import com.turkcell.product_service.application.category.dto.response.CategoryResponse;
import com.turkcell.product_service.application.category.exception.CategoryNotFoundException;
import com.turkcell.product_service.application.category.mapper.CategoryMapper;
import com.turkcell.product_service.domain.model.category.Category;
import com.turkcell.product_service.domain.model.category.CategoryId;
import com.turkcell.product_service.domain.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetCategoryByIdUseCase {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public GetCategoryByIdUseCase(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public CategoryResponse getCategoryById(UUID categoryId) {
        Category category = categoryRepository.findById(new CategoryId(categoryId))
                .orElseThrow(() -> new CategoryNotFoundException("Category not found!"));

        return categoryMapper.toResponse(category);
    }
}
