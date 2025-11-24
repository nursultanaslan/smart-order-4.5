package com.turkcell.product_service.application.mapper;

import com.turkcell.product_service.application.dto.DeletedProductResponse;
import com.turkcell.product_service.domain.model.Product;
import org.springframework.stereotype.Component;

@Component
public class DeletedProductMapper {

    public DeletedProductResponse toResponse(Product product){
        return new DeletedProductResponse(
                product.id().value()
        );
    }
}
