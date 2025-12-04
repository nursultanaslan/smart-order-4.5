package com.turkcell.product_service.application.category.usecases;

import com.turkcell.product_service.application.category.dto.request.CreateCategoryRequest;
import com.turkcell.product_service.application.category.dto.response.CategoryResponse;
import com.turkcell.product_service.application.category.exception.CategoryNameAlreadyExistsException;
import com.turkcell.product_service.application.category.mapper.CategoryMapper;
import com.turkcell.product_service.domain.model.category.Category;
import com.turkcell.product_service.domain.policy.CategoryNameUniquePolicy;
import com.turkcell.product_service.domain.repository.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class CreateCategoryUseCase {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CategoryNameUniquePolicy categoryNameUniquePolicy;

    public CreateCategoryUseCase(CategoryRepository categoryRepository, CategoryMapper categoryMapper, CategoryNameUniquePolicy categoryNameUniquePolicy) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.categoryNameUniquePolicy = categoryNameUniquePolicy;
    }

    public CategoryResponse create(@Valid CreateCategoryRequest request) {
        if (!categoryNameUniquePolicy.isUnique(request.categoryName())){
            throw new CategoryNameAlreadyExistsException("Category with name " + request.categoryName() + " already exists!");
        }

        Category category = categoryMapper.toDomain(request);
        category =  categoryRepository.save(category);
        return categoryMapper.toResponse(category);
    }
}
