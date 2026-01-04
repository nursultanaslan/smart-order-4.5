package com.turkcell.product_service.application.brand.usecases;

import com.turkcell.product_service.application.brand.dto.response.BrandResponse;
import com.turkcell.product_service.application.brand.dto.request.CreateBrandRequest;
import com.turkcell.product_service.application.brand.exception.BrandNameAlreadyExistsException;
import com.turkcell.product_service.application.brand.mapper.BrandMapper;
import com.turkcell.product_service.domain.model.brand.Brand;
import com.turkcell.product_service.domain.policy.BrandNameUniquePolicy;
import com.turkcell.product_service.domain.repository.BrandRepository;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class CreateBrandUseCase {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;
    private final BrandNameUniquePolicy brandNameUniquePolicy;

    public CreateBrandUseCase(BrandRepository brandRepository, BrandMapper brandMapper, BrandNameUniquePolicy brandNameUniquePolicy) {
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
        this.brandNameUniquePolicy = brandNameUniquePolicy;
    }

    @PreAuthorize("hasAnyAuthority('BRAND_CREATE')")
    public BrandResponse createBrand(@Valid CreateBrandRequest request) {

        if (!brandNameUniquePolicy.isUnique(request.brandName())){
            throw new BrandNameAlreadyExistsException("Brand with name " + request.brandName() + " already exists!");
        }

        Brand brand = brandMapper.toDomain(request);
        brand = brandRepository.save(brand);
        return brandMapper.toResponse(brand);
    }
}
