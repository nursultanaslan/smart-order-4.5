package com.turkcell.product_service.application.brand.usecases;

import com.turkcell.product_service.domain.repository.BrandRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateBrandUseCase {

    private final BrandRepository brandRepository;

    public CreateBrandUseCase(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }
}
