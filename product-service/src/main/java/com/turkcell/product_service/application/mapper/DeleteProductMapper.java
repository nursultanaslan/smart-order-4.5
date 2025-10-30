package com.turkcell.product_service.application.mapper;

import com.turkcell.product_service.application.dto.response.DeleteProductResponse;
import com.turkcell.product_service.domain.model.Product;
import org.springframework.stereotype.Component;

@Component
public class DeleteProductMapper {

    public DeleteProductResponse toResponse(Product product){
        return new DeleteProductResponse(
                product.id().value()
        );
    }
}
