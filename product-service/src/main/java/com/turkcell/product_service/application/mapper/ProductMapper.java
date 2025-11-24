package com.turkcell.product_service.application.mapper;

import com.turkcell.product_service.application.command.CreateProductCommand;
import com.turkcell.product_service.application.dto.ProductResponse;
import com.turkcell.product_service.domain.model.Money;
import com.turkcell.product_service.domain.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toDomain(CreateProductCommand command) {
        return Product.create(
                command.name(),
                new Money(command.amount(), command.currency()),
                command.description(),
                command.stock()
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
