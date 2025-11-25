package com.turkcell.product_service.application.query;

import com.turkcell.product_service.application.dto.ProductResponse;
import com.turkcell.product_service.application.exception.ProductNotFoundException;
import com.turkcell.product_service.core.cqrs.QueryHandler;
import com.turkcell.product_service.domain.model.Product;
import com.turkcell.product_service.domain.model.ProductId;
import com.turkcell.product_service.domain.repository.ProductRepository;
import org.springframework.stereotype.Component;


@Component
public class GetProductByIdQueryHandler implements QueryHandler<GetProductByIdQuery, ProductResponse> {

    private final ProductRepository productRepository;

    public GetProductByIdQueryHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponse handle(GetProductByIdQuery query) {

        Product product = productRepository.findById(new ProductId(query.productId()))
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

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
