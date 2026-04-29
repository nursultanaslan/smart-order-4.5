package com.turkcell.product_service.application.product.command;

import com.turkcell.product_service.application.product.dto.ProductResponse;
import com.turkcell.product_service.application.product.mapper.ProductMapper;
import com.turkcell.product_service.core.cqrs.CommandHandler;
import com.turkcell.product_service.domain.exception.BrandNotFoundException;
import com.turkcell.product_service.domain.exception.CategoryNotFoundException;
import com.turkcell.product_service.domain.model.brand.BrandId;
import com.turkcell.product_service.domain.model.category.CategoryId;
import com.turkcell.product_service.domain.model.product.Product;
import com.turkcell.product_service.domain.repository.BrandRepository;
import com.turkcell.product_service.domain.repository.CategoryRepository;
import com.turkcell.product_service.domain.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class CreateProductCommandHandler implements CommandHandler<CreateProductCommand, ProductResponse> {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    public CreateProductCommandHandler(ProductMapper productMapper, ProductRepository productRepository, BrandRepository brandRepository, CategoryRepository categoryRepository) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
    }

    //TODO: brandId ve categoryId ye ait verilerin
    // veritabanında bulunup bulunmadığını kontrol etmeden ekliyor.

    @Override
    @Transactional
    public ProductResponse handle(CreateProductCommand command) {

        BrandId brandId = new BrandId(command.brandId());
        if (!brandRepository.existsByBrandId(brandId)) {
            throw new BrandNotFoundException(brandId);
        }

        CategoryId categoryId = new CategoryId(command.categoryId());
        if (!categoryRepository.existsByCategorId(categoryId)) {
            throw new CategoryNotFoundException(categoryId);
        }

        Product product = productMapper.toDomain(command);
        product = productRepository.save(product);
        return productMapper.toResponse(product);
    }
}
