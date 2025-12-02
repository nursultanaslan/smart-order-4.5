package com.turkcell.product_service.application.brand.mapper;

import com.turkcell.product_service.application.brand.dto.request.UpdateBrandRequest;
import com.turkcell.product_service.application.brand.dto.response.BrandResponse;
import com.turkcell.product_service.application.brand.dto.request.CreateBrandRequest;
import com.turkcell.product_service.application.brand.dto.response.DeletedBrandResponse;
import com.turkcell.product_service.domain.model.brand.Brand;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper {

    public Brand toDomain(CreateBrandRequest request) {
        return Brand.create(
                request.brandName()
        );
    }

    public BrandResponse toResponse(Brand brand) {
        return new BrandResponse(
                brand.id().value(),
                brand.brandName()
        );
    }

    public DeletedBrandResponse toDeletedResponse(Brand brand) {
        return new DeletedBrandResponse(
                brand.id().value()
        );
    }

    public BrandResponse toBrandResponse(UpdateBrandRequest request) {
        return new BrandResponse(
                request.brandId(),
                request.brandName()
        );
    }
}
