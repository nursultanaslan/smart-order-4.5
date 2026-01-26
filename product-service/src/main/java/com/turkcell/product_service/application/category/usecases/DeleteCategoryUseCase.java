package com.turkcell.product_service.application.category.usecases;

import com.turkcell.product_service.application.category.dto.request.DeleteCategoryRequest;
import com.turkcell.product_service.application.category.dto.response.DeletedCategoryResponse;
import com.turkcell.product_service.domain.exception.CategoryNotFoundException;
import com.turkcell.product_service.application.category.mapper.CategoryMapper;
import com.turkcell.product_service.domain.model.category.Category;
import com.turkcell.product_service.domain.model.category.CategoryId;
import com.turkcell.product_service.domain.repository.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


@Service
@Validated
public class DeleteCategoryUseCase {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public DeleteCategoryUseCase(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public DeletedCategoryResponse deleteCategory(@Valid DeleteCategoryRequest request) {
        Category category = categoryRepository.findById(new CategoryId(request.categoryId()))
                .orElseThrow(() -> new CategoryNotFoundException("Category not found!"));
        categoryRepository.delete(category);
        return categoryMapper.toDeletedResponse(category);
    }
}
