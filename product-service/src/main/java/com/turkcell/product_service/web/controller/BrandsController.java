package com.turkcell.product_service.web.controller;

import com.turkcell.product_service.application.brand.dto.request.DeleteBrandRequest;
import com.turkcell.product_service.application.brand.dto.request.UpdateBrandRequest;
import com.turkcell.product_service.application.brand.dto.response.BrandResponse;
import com.turkcell.product_service.application.brand.dto.request.CreateBrandRequest;
import com.turkcell.product_service.application.brand.dto.response.DeletedBrandResponse;
import com.turkcell.product_service.application.brand.usecases.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/brands")
public class BrandsController {

    private final CreateBrandUseCase createBrandUseCase;
    private final DeleteBrandUseCase  deleteBrandUseCase;
    private final UpdateBrandUseCase updateBrandUseCase;

    public BrandsController(CreateBrandUseCase createBrandUseCase, DeleteBrandUseCase deleteBrandUseCase, UpdateBrandUseCase updateBrandUseCase) {
        this.createBrandUseCase = createBrandUseCase;
        this.deleteBrandUseCase = deleteBrandUseCase;
        this.updateBrandUseCase = updateBrandUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BrandResponse create(@RequestBody @Valid CreateBrandRequest request) {
        return createBrandUseCase.createBrand(request);
    }

    @DeleteMapping("/{id}")
    public DeletedBrandResponse delete(@PathVariable("id") @Valid UUID brandId) {
        return deleteBrandUseCase.deleteBrand(new DeleteBrandRequest(brandId));
    }

    @PutMapping("/update/{id}")
    public BrandResponse update(@PathVariable("id") UUID brandId, @RequestBody @Valid UpdateBrandRequest request) {
        return updateBrandUseCase.updateBrand(new UpdateBrandRequest(brandId, request.brandName()));
    }
}
