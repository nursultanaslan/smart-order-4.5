package com.turkcell.product_service.application.product.command;

import com.turkcell.product_service.application.product.dto.DeletedProductResponse;
import com.turkcell.product_service.domain.exception.ProductNotFoundException;
import com.turkcell.product_service.application.product.mapper.ProductMapper;
import com.turkcell.product_service.core.cqrs.CommandHandler;
import com.turkcell.product_service.domain.model.product.Product;
import com.turkcell.product_service.domain.model.product.ProductId;
import com.turkcell.product_service.domain.repository.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public class DeleteProductCommandHandler implements CommandHandler<DeleteProductCommand, DeletedProductResponse> {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public DeleteProductCommandHandler(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;

    }

    @Override
    public DeletedProductResponse handle(DeleteProductCommand command) {

        Product product = productRepository.findById(new ProductId(command.productId()))
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        productRepository.delete(product);
        return productMapper.toDeletedResponse(product);
    }
}
