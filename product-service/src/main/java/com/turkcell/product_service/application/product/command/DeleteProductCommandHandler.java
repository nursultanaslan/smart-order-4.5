package com.turkcell.product_service.application.product.command;

import com.turkcell.product_service.application.product.dto.DeletedProductResponse;
import com.turkcell.product_service.application.product.exception.ProductNotFoundException;
import com.turkcell.product_service.application.product.mapper.DeletedProductMapper;
import com.turkcell.product_service.core.cqrs.CommandHandler;
import com.turkcell.product_service.domain.model.product.Product;
import com.turkcell.product_service.domain.model.product.ProductId;
import com.turkcell.product_service.domain.repository.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public class DeleteProductCommandHandler implements CommandHandler<DeleteProductCommand, DeletedProductResponse> {

    private final ProductRepository productRepository;
    private final DeletedProductMapper deletedProductMapper;

    public DeleteProductCommandHandler(ProductRepository productRepository, DeletedProductMapper deletedProductMapper) {
        this.productRepository = productRepository;
        this.deletedProductMapper = deletedProductMapper;
    }

    @Override
    public DeletedProductResponse handle(DeleteProductCommand command) {

        Product product = productRepository.findById(new ProductId(command.productId()))
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        productRepository.delete(product);
        return deletedProductMapper.toResponse(product);
    }
}
