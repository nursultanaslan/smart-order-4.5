package com.turkcell.product_service.web.controller;

import com.turkcell.product_service.application.category.dto.request.CreateCategoryRequest;
import com.turkcell.product_service.application.category.dto.request.DeleteCategoryRequest;
import com.turkcell.product_service.application.category.dto.request.UpdateCategoryRequest;
import com.turkcell.product_service.application.category.dto.response.CategoryListResponse;
import com.turkcell.product_service.application.category.dto.response.CategoryResponse;
import com.turkcell.product_service.application.category.dto.response.DeletedCategoryResponse;
import com.turkcell.product_service.application.category.usecases.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoriesController {

    private final CreateCategoryUseCase createCategoryUseCase;
    private final DeleteCategoryUseCase deleteCategoryUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;
    private final GetCategoryByIdUseCase getCategoryByIdUseCase;
    private final GetAllCategoriesUseCase getAllCategoriesUseCase;


    public CategoriesController(CreateCategoryUseCase createCategoryUseCase,
                                DeleteCategoryUseCase deleteCategoryUseCase,
                                UpdateCategoryUseCase updateCategoryUseCase, GetCategoryByIdUseCase getCategoryByIdUseCase, GetAllCategoriesUseCase getAllCategoriesUseCase) {
        this.createCategoryUseCase = createCategoryUseCase;
        this.deleteCategoryUseCase = deleteCategoryUseCase;
        this.updateCategoryUseCase = updateCategoryUseCase;
        this.getCategoryByIdUseCase = getCategoryByIdUseCase;
        this.getAllCategoriesUseCase = getAllCategoriesUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse create(@RequestBody CreateCategoryRequest request) {
        return createCategoryUseCase.create(request);
    }

    @GetMapping()
    public CategoryListResponse getAll() {
        return getAllCategoriesUseCase.findAllCategories();
    }

    @GetMapping("/{id}")
    public CategoryResponse getById(@PathVariable("id") UUID categoryId) {
        return getCategoryByIdUseCase.getCategoryById(categoryId);
    }

    @DeleteMapping("/{id}")
    public DeletedCategoryResponse deleteById(@PathVariable("id") @Valid UUID categoryId) {
        return deleteCategoryUseCase.deleteCategory(new DeleteCategoryRequest(categoryId));
    }

    @PutMapping("/update/{id}")
    public CategoryResponse update(@PathVariable("id") UUID categoryId, @RequestBody @Valid UpdateCategoryRequest request) {
        return updateCategoryUseCase.updateCategory(new UpdateCategoryRequest(categoryId, request.categoryName()));
    }

}
