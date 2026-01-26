package com.turkcell.product_service.application.brand.usecases;

import com.turkcell.product_service.application.brand.dto.response.BrandResponse;
import com.turkcell.product_service.domain.exception.BrandNotFoundException;
import com.turkcell.product_service.application.brand.mapper.BrandMapper;
import com.turkcell.product_service.domain.model.brand.Brand;
import com.turkcell.product_service.domain.model.brand.BrandId;
import com.turkcell.product_service.domain.repository.BrandRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetBrandByIdUseCase {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    public GetBrandByIdUseCase(BrandRepository brandRepository, BrandMapper brandMapper) {
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
    }

    public BrandResponse getBrandById(UUID brandId) {
        Brand brand = brandRepository.findById(new BrandId(brandId))
                .orElseThrow(() -> new BrandNotFoundException("Brand not found!"));

        return brandMapper.toResponse(brand);

    }
}
