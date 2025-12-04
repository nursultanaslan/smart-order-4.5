package com.turkcell.product_service.application.brand.usecases;

import com.turkcell.product_service.application.brand.dto.response.BrandListResponse;
import com.turkcell.product_service.application.brand.dto.response.BrandResponse;
import com.turkcell.product_service.application.brand.mapper.BrandMapper;
import com.turkcell.product_service.domain.model.brand.Brand;
import com.turkcell.product_service.domain.repository.BrandRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllBrandsUseCase {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    public GetAllBrandsUseCase(BrandRepository brandRepository, BrandMapper brandMapper) {
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
    }

    public BrandListResponse getAllBrands() {
        List<Brand> brands = brandRepository.findAll();

        List<BrandResponse> responses = brands
                .stream()
                .map(brandMapper::toResponse)
                .toList();

        return new BrandListResponse(responses);
    }
}
