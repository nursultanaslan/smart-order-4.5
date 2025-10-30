package com.turkcell.product_service.application.mapper;

import com.turkcell.product_service.application.dto.request.CreateProductRequest;
import com.turkcell.product_service.application.dto.response.DeleteProductResponse;
import com.turkcell.product_service.application.dto.response.ProductResponse;
import com.turkcell.product_service.domain.model.Money;
import com.turkcell.product_service.domain.model.Product;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProductMapper {

    public Product toDomain(CreateProductRequest request) {
        return Product.create(
                request.name(),
                new Money(request.amount(), request.currency()),
                request.description(),
                request.stock()
        );
    }


    public ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.id().value(),
                product.name(),
                product.price().amount(),
                product.price().currency(),
                product.description(),
                product.stock()
        );
    }

}
