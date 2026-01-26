package com.turkcell.product_service.application.category.usecases;

import com.turkcell.product_service.application.category.dto.request.UpdateCategoryRequest;
import com.turkcell.product_service.application.category.dto.response.CategoryResponse;
import com.turkcell.product_service.domain.exception.CategoryNotFoundException;
import com.turkcell.product_service.application.category.mapper.CategoryMapper;
import com.turkcell.product_service.domain.model.category.Category;
import com.turkcell.product_service.domain.model.category.CategoryId;
import com.turkcell.product_service.domain.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateCategoryUseCase {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public UpdateCategoryUseCase(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public CategoryResponse updateCategory(UpdateCategoryRequest request) {
        Category category = categoryRepository.findById(new CategoryId(request.categoryId()))
                .orElseThrow(() -> new CategoryNotFoundException("Category not found!"));

        if (request.categoryName() != null && !request.categoryName().isEmpty()) {
            category.updateCategoryName(request.categoryName());
        }
        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.toResponse(updatedCategory);
    }
}
