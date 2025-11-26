package com.turkcell.product_service.application.mapper;

import com.turkcell.product_service.application.command.CreateProductCommand;
import com.turkcell.product_service.application.dto.ProductDto;
import com.turkcell.product_service.application.dto.ProductResponse;
import com.turkcell.product_service.domain.model.brand.Brand;
import com.turkcell.product_service.domain.model.brand.BrandId;
import com.turkcell.product_service.domain.model.category.CategoryId;
import com.turkcell.product_service.domain.model.product.Money;
import com.turkcell.product_service.domain.model.product.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toDomain(CreateProductCommand command) {
        return Product.create(
                command.productName(),
                new Money(command.amount(), command.currency()),
                command.description(),
                command.stock(),
                new BrandId(command.brandId()),
                new CategoryId(command.categoryId())
        );
    }


    public ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.id().value(),
                product.productName(),
                product.price().amount(),
                product.price().currency(),
                product.description(),
                product.stock()
        );
    }

    public ProductDto toDto(Product product) {
        return new ProductDto(
                product.id().value(),
                product.productName(),
                product.stock(),
                product.price().amount(),
                product.price().currency()
        );
    }

}
