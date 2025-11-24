package com.turkcell.product_service.application.query;

import com.turkcell.product_service.application.dto.PageableProductResponse;
import com.turkcell.product_service.core.cqrs.QueryHandler;
import com.turkcell.product_service.domain.repository.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
public class FindProductsByNameQueryHandler implements QueryHandler<FindProductsByNameQuery, PageableProductResponse> {

    private final ProductRepository productRepository;

    public FindProductsByNameQueryHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public PageableProductResponse handle(FindProductsByNameQuery query) {

        productRepository.findByName(query.productName(), PageRequest.of(query.pageIndex(), query.pageSize()));

        return new PageableProductResponse(
                query.productName());
    }
}
