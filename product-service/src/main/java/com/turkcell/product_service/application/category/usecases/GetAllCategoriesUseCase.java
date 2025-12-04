package com.turkcell.product_service.application.category.usecases;

import com.turkcell.product_service.application.category.dto.response.CategoryListResponse;
import com.turkcell.product_service.application.category.dto.response.CategoryResponse;
import com.turkcell.product_service.application.category.mapper.CategoryMapper;
import com.turkcell.product_service.domain.model.category.Category;
import com.turkcell.product_service.domain.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllCategoriesUseCase {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public GetAllCategoriesUseCase(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public CategoryListResponse findAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        List<CategoryResponse> responses = categories
                .stream()
                .map(categoryMapper::toResponse)
                .toList();

        return new CategoryListResponse(responses);
    }
}
