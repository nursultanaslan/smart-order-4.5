package com.turkcell.product_service.infrastructure.policy;

import com.turkcell.product_service.domain.policy.BrandNameUniquePolicy;
import com.turkcell.product_service.domain.repository.BrandRepository;
import org.springframework.stereotype.Component;

@Component
public class JpaBrandNameUniqueChecker implements BrandNameUniquePolicy {

    private final BrandRepository brandRepository;

    public JpaBrandNameUniqueChecker(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public boolean isUnique(String brandName) {
        return !brandRepository.existsByBrandNameIgnoreCase(brandName);
    }
}
