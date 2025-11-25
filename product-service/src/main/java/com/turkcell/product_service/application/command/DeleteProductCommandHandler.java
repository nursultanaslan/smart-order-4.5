package com.turkcell.product_service.application.command;

import com.turkcell.product_service.application.dto.DeletedProductResponse;
import com.turkcell.product_service.application.exception.ProductNotFoundException;
import com.turkcell.product_service.application.mapper.DeletedProductMapper;
import com.turkcell.product_service.core.cqrs.CommandHandler;
import com.turkcell.product_service.domain.model.Product;
import com.turkcell.product_service.domain.model.ProductId;
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
