package com.turkcell.product_service.web;

import com.turkcell.product_service.application.category.dto.request.CreateCategoryRequest;
import com.turkcell.product_service.application.category.dto.request.DeleteCategoryRequest;
import com.turkcell.product_service.application.category.dto.response.CategoryResponse;
import com.turkcell.product_service.application.category.dto.response.DeletedCategoryResponse;
import com.turkcell.product_service.application.category.usecases.CreateCategoryUseCase;
import com.turkcell.product_service.application.category.usecases.DeleteCategoryUseCase;
import com.turkcell.product_service.application.category.usecases.GetCategoryByIdUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoriesController {

    private final CreateCategoryUseCase createCategoryUseCase;
    private final DeleteCategoryUseCase deleteCategoryUseCase;
    private final GetCategoryByIdUseCase getCategoryByIdUseCase;

    public CategoriesController(CreateCategoryUseCase createCategoryUseCase, DeleteCategoryUseCase deleteCategoryUseCase, GetCategoryByIdUseCase getCategoryByIdUseCase) {
        this.createCategoryUseCase = createCategoryUseCase;
        this.deleteCategoryUseCase = deleteCategoryUseCase;
        this.getCategoryByIdUseCase = getCategoryByIdUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse create(@RequestBody CreateCategoryRequest request) {
        return createCategoryUseCase.create(request);
    }

    @GetMapping("/{categoryId}")
    public CategoryResponse getById(@PathVariable("categoryId") UUID categoryId) {
        return getCategoryByIdUseCase.getCategoryById(categoryId);
    }

    @DeleteMapping("/{categoryId}")
    public DeletedCategoryResponse deleteById(@PathVariable("categoryId") @Valid UUID categoryId) {
        return deleteCategoryUseCase.deleteCategory(new DeleteCategoryRequest(categoryId));
    }
}
