package com.turkcell.product_service.application.product.command;

import com.turkcell.product_service.application.product.dto.ProductResponse;
import com.turkcell.product_service.application.product.exception.ProductNotFoundException;
import com.turkcell.product_service.application.product.mapper.ProductMapper;
import com.turkcell.product_service.core.cqrs.CommandHandler;
import com.turkcell.product_service.domain.event.ProductPriceChangedEvent;
import com.turkcell.product_service.domain.model.product.Money;
import com.turkcell.product_service.domain.model.product.Product;
import com.turkcell.product_service.domain.model.product.ProductId;
import com.turkcell.product_service.domain.repository.DomainEventsPublisher;
import com.turkcell.product_service.domain.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class UpdateProductPriceCommandHandler implements CommandHandler<UpdateProductPriceCommand, ProductResponse> {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final DomainEventsPublisher domainEventsPublisher;

    public UpdateProductPriceCommandHandler(ProductRepository productRepository, ProductMapper productMapper, DomainEventsPublisher domainEventsPublisher) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.domainEventsPublisher = domainEventsPublisher;
    }

    @Override
    @Transactional
    public ProductResponse handle(UpdateProductPriceCommand command) {

        Product product = productRepository.findById(new ProductId(command.productId()))
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        Money money = new Money(command.newAmount(), command.currency());
        product.changePrice(money);  //domain behaviour

        productRepository.save(product);

        ProductPriceChangedEvent event = new ProductPriceChangedEvent(
                product.id(),
                product.price()
        );

        domainEventsPublisher.publish(event);

        return productMapper.toResponse(product);
    }
}
