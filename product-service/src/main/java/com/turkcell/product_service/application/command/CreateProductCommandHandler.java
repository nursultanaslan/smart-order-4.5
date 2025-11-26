package com.turkcell.product_service.application.command;

import com.turkcell.product_service.application.dto.ProductResponse;
import com.turkcell.product_service.application.mapper.ProductMapper;
import com.turkcell.product_service.core.cqrs.CommandHandler;
import com.turkcell.product_service.domain.model.product.Product;
import com.turkcell.product_service.domain.repository.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public class CreateProductCommandHandler implements CommandHandler<CreateProductCommand, ProductResponse> {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    public CreateProductCommandHandler(ProductMapper productMapper, ProductRepository productRepository) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponse handle(CreateProductCommand command) {
        Product product = productMapper.toDomain(command);
        product = productRepository.save(product);
        return productMapper.toResponse(product);
    }
}
