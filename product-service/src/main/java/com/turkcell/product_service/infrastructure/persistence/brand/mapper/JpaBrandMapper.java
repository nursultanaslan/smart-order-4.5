package com.turkcell.product_service.infrastructure.persistence.brand.mapper;

import com.turkcell.product_service.domain.model.brand.Brand;
import com.turkcell.product_service.domain.model.brand.BrandId;
import com.turkcell.product_service.infrastructure.persistence.brand.model.JpaBrandEntity;
import org.springframework.stereotype.Component;

@Component
public class JpaBrandMapper {

    public JpaBrandEntity toEntity(Brand brand) {
        return new JpaBrandEntity(
                brand.id().value(),
                brand.brandName()
        );
    }

    public Brand toDomain(JpaBrandEntity entity) {
        return Brand.rehydrate(
                new BrandId(entity.id()),
                entity.brandName()
        );
    }
}
