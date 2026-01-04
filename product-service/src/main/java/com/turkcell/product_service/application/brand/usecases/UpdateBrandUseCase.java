package com.turkcell.product_service.application.brand.usecases;

import com.turkcell.product_service.application.brand.dto.request.UpdateBrandRequest;
import com.turkcell.product_service.application.brand.dto.response.BrandResponse;
import com.turkcell.product_service.application.brand.exception.BrandNotFoundException;
import com.turkcell.product_service.application.brand.mapper.BrandMapper;
import com.turkcell.product_service.domain.model.brand.Brand;
import com.turkcell.product_service.domain.model.brand.BrandId;
import com.turkcell.product_service.domain.repository.BrandRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;


@Service
public class UpdateBrandUseCase {

    private final BrandRepository brandRepository;
    private final BrandMapper  brandMapper;

    public UpdateBrandUseCase(BrandRepository brandRepository, BrandMapper brandMapper) {
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
    }

    @PreAuthorize("hasAnyAuthority('BRAND_UPDATE')")
    public BrandResponse updateBrand(UpdateBrandRequest request) {

        Brand brand = brandRepository.findById(new BrandId(request.brandId()))
                .orElseThrow(() -> new BrandNotFoundException("Brand not found!"));

        if (request.brandName() != null) {
            brand.updateBrandName(
                    request.brandName().trim()
            );
        }
        Brand updatedBrand = brandRepository.save(brand);

        return brandMapper.toResponse(updatedBrand);
    }
}
