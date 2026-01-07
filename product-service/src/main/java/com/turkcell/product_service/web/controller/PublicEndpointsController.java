package com.turkcell.product_service.web.controller;

import com.turkcell.product_service.application.brand.dto.response.BrandListResponse;
import com.turkcell.product_service.application.brand.dto.response.BrandResponse;
import com.turkcell.product_service.application.brand.usecases.GetAllBrandsUseCase;
import com.turkcell.product_service.application.brand.usecases.GetBrandByIdUseCase;
import com.turkcell.product_service.application.category.dto.response.CategoryListResponse;
import com.turkcell.product_service.application.category.dto.response.CategoryResponse;
import com.turkcell.product_service.application.category.usecases.GetAllCategoriesUseCase;
import com.turkcell.product_service.application.category.usecases.GetCategoryByIdUseCase;
import com.turkcell.product_service.application.product.dto.GetProductByIdResponse;
import com.turkcell.product_service.application.product.dto.PageableProductResponse;
import com.turkcell.product_service.application.product.query.FindProductsByNameQuery;
import com.turkcell.product_service.application.product.query.GetAllProductsByCategoryIdQuery;
import com.turkcell.product_service.application.product.query.GetProductByIdQuery;
import com.turkcell.product_service.core.cqrs.QueryHandler;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/public")
public class PublicEndpointsController {

    private final GetBrandByIdUseCase getBrandByIdUseCase;
    private final GetAllBrandsUseCase getAllBrandsUseCase;
    private final GetCategoryByIdUseCase getCategoryByIdUseCase;
    private final GetAllCategoriesUseCase getAllCategoriesUseCase;
    private final QueryHandler<GetProductByIdQuery, GetProductByIdResponse> getProductByIdQueryHandler;
    private final QueryHandler<FindProductsByNameQuery, PageableProductResponse>  findProductsByNameQueryHandler;
    private final QueryHandler<GetAllProductsByCategoryIdQuery, PageableProductResponse>  getProductsByCategoryIdQuery;


    public PublicEndpointsController(GetBrandByIdUseCase getBrandByIdUseCase, GetAllBrandsUseCase getAllBrandsUseCase, GetCategoryByIdUseCase getCategoryByIdUseCase, GetAllCategoriesUseCase getAllCategoriesUseCase, QueryHandler<GetProductByIdQuery, GetProductByIdResponse> getProductByIdQueryHandler, QueryHandler<FindProductsByNameQuery, PageableProductResponse> findProductsByNameQueryHandler, QueryHandler<GetAllProductsByCategoryIdQuery, PageableProductResponse> getProductsByCategoryIdQuery) {
        this.getBrandByIdUseCase = getBrandByIdUseCase;
        this.getAllBrandsUseCase = getAllBrandsUseCase;
        this.getCategoryByIdUseCase = getCategoryByIdUseCase;
        this.getAllCategoriesUseCase = getAllCategoriesUseCase;
        this.getProductByIdQueryHandler = getProductByIdQueryHandler;
        this.findProductsByNameQueryHandler = findProductsByNameQueryHandler;
        this.getProductsByCategoryIdQuery = getProductsByCategoryIdQuery;
    }


    @GetMapping("/brands")
    public BrandListResponse findAll() {
        return getAllBrandsUseCase.getAllBrands();
    }

    @GetMapping("/brands/{id}")
    public BrandResponse findById(@PathVariable("id") UUID brandId) {
        return getBrandByIdUseCase.getBrandById(brandId);
    }

    @GetMapping("/categories")
    public CategoryListResponse getAll() {
        return getAllCategoriesUseCase.findAllCategories();
    }

    @GetMapping("/categories/{id}")
    public CategoryResponse getById(@PathVariable("id") UUID categoryId) {
        return getCategoryByIdUseCase.getCategoryById(categoryId);
    }

    @GetMapping("/products/{id}")
    public GetProductByIdResponse getProductById(@PathVariable("id") UUID productId) {
        return getProductByIdQueryHandler.handle(new GetProductByIdQuery(productId));
    }

    @GetMapping("/products/search")
    public PageableProductResponse findProductsByNameQuery(
            @RequestParam("productName") String productName,
            @RequestParam("pageIndex") Integer pageIndex,
            @RequestParam("pageSize") Integer pageSize ) {
        return findProductsByNameQueryHandler.handle(new FindProductsByNameQuery(productName, pageIndex, pageSize));
    }

    @GetMapping("/products/search-by-category")
    public PageableProductResponse getProductsByCategoryIdQuery(
            @RequestParam("categoryId") UUID categoryId,
            @RequestParam("pageIndex") Integer pageIndex,
            @RequestParam("pageSize") Integer pageSize) {
        return getProductsByCategoryIdQuery.handle(new GetAllProductsByCategoryIdQuery(categoryId, pageIndex, pageSize));

    }
}
