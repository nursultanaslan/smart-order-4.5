package com.turkcell.product_service.application.brand.usecases;

import com.turkcell.product_service.application.brand.dto.request.DeleteBrandRequest;
import com.turkcell.product_service.application.brand.dto.response.DeletedBrandResponse;
import com.turkcell.product_service.application.brand.exception.BrandNotFoundException;
import com.turkcell.product_service.application.brand.mapper.BrandMapper;
import com.turkcell.product_service.domain.model.brand.Brand;
import com.turkcell.product_service.domain.model.brand.BrandId;
import com.turkcell.product_service.domain.repository.BrandRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


@Service
@Validated
public class DeleteBrandUseCase {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    public DeleteBrandUseCase(BrandRepository brandRepository, BrandMapper brandMapper) {
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
    }

    public DeletedBrandResponse deleteBrand(@Valid DeleteBrandRequest request) {
        Brand brand = brandRepository.findById(new BrandId(request.brandId()))
                .orElseThrow(() -> new BrandNotFoundException("Brand not found."));

        brandRepository.delete(brand);
        return brandMapper.toDeletedResponse(brand);
    }
}
