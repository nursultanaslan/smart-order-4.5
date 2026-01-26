package com.turkcell.product_service.application.product.command;

import com.turkcell.product_service.application.product.dto.ProductResponse;
import com.turkcell.product_service.domain.exception.ProductNotFoundException;
import com.turkcell.product_service.application.product.mapper.ProductMapper;
import com.turkcell.product_service.core.cqrs.CommandHandler;
import com.turkcell.product_service.domain.model.product.Product;
import com.turkcell.product_service.domain.model.product.ProductId;
import com.turkcell.product_service.domain.repository.ProductRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class UpdateProductDetailsCommandHandler implements CommandHandler<UpdateProductDetailsCommand, ProductResponse> {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public UpdateProductDetailsCommandHandler(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    @Transactional
    public ProductResponse handle (UpdateProductDetailsCommand command) {

        ProductId id = new ProductId(command.productId());

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        if (command.productName() != null) {
            product.updateProductName(command.productName());
        }

        if (command.description() != null) {
            product.updateDescription(command.description());
        }

        if (command.stock() != null){
            product.updateStock(command.stock());
        }

        Product updatedProduct =  productRepository.save(product);

        return productMapper.toResponse(updatedProduct);
    }
}
