package com.turkcell.product_service.application.product.mapper;

import com.turkcell.product_service.application.product.dto.DeletedProductResponse;
import com.turkcell.product_service.domain.model.product.Product;
import org.springframework.stereotype.Component;

@Component
public class DeletedProductMapper {

    public DeletedProductResponse toResponse(Product product){
        return new DeletedProductResponse(
                product.id().value()
        );
    }
}
