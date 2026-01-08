package com.turkcell.product_service.web.controller;

import com.turkcell.product_service.application.product.command.*;
import com.turkcell.product_service.application.product.dto.DeletedProductResponse;
import com.turkcell.product_service.application.product.dto.ProductResponse;
import com.turkcell.product_service.core.cqrs.CommandHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/api/v1/products")
public class ProductsController {

    private final CommandHandler<CreateProductCommand, ProductResponse>  createProductCommandHandler;
    private final CommandHandler<DeleteProductCommand, DeletedProductResponse>  deleteProductCommandHandler;
    private final CommandHandler<UpdateProductDetailsCommand, ProductResponse>  updateProductCommandHandler;
    private final CommandHandler<DecreaseProductStockCommand, ProductResponse>  decreaseProductStockCommand;
    private final CommandHandler<IncreaseProductStockCommand,  ProductResponse>  increaseProductStockCommand;
    private final CommandHandler<UpdateProductPriceCommand, ProductResponse>  updateProductPriceCommand;

    public ProductsController(CommandHandler<CreateProductCommand, ProductResponse> createProductCommandHandler,
                              CommandHandler<DeleteProductCommand, DeletedProductResponse> deleteProductCommandHandler,
                              CommandHandler<UpdateProductDetailsCommand, ProductResponse> updateProductCommandHandler,
                              CommandHandler<DecreaseProductStockCommand, ProductResponse> decreaseProductStockCommand,
                              CommandHandler<IncreaseProductStockCommand, ProductResponse> increaseProductStockCommand, CommandHandler<UpdateProductPriceCommand, ProductResponse> updateProductPriceCommand) {
        this.createProductCommandHandler = createProductCommandHandler;
        this.deleteProductCommandHandler = deleteProductCommandHandler;
        this.updateProductCommandHandler = updateProductCommandHandler;
        this.decreaseProductStockCommand = decreaseProductStockCommand;
        this.increaseProductStockCommand = increaseProductStockCommand;
        this.updateProductPriceCommand = updateProductPriceCommand;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@RequestBody CreateProductCommand command) {
        return createProductCommandHandler.handle(command);
    }

    @DeleteMapping("/{id}")
    public DeletedProductResponse deleteProduct(@PathVariable("id") UUID productId) {
        return  deleteProductCommandHandler.handle(new DeleteProductCommand(productId));
    }



    @PutMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable("id") UUID productId, @RequestBody UpdateProductDetailsCommand command) {
        UpdateProductDetailsCommand finalCommand = new UpdateProductDetailsCommand(
                productId,
                command.productName(),
                command.description(),
                command.stock()
        );
        return updateProductCommandHandler.handle(finalCommand);
    }

    @PutMapping("/{id}")
    public ProductResponse updateProductPrice(@PathVariable("id") UUID productId, @RequestBody UpdateProductPriceCommand command) {
        UpdateProductPriceCommand finalCommand = new UpdateProductPriceCommand(
                productId,
                command.newAmount(),
                command.currency()
        );
        return updateProductPriceCommand.handle(finalCommand);
    }

    @PutMapping("/{id}/stock/decrease")
    public ProductResponse decreaseStock(@PathVariable("id") UUID productId, @RequestParam("decreaseQuantity") Integer decreaseQuantity) {
        return decreaseProductStockCommand.handle(new DecreaseProductStockCommand(productId, decreaseQuantity));
    }

    @PutMapping("/{id}/stock/increase")
    public ProductResponse increaseStock(@PathVariable("id") UUID productId, @RequestParam("increaseQuantity") Integer increaseQuantity) {
        return increaseProductStockCommand.handle(new IncreaseProductStockCommand(productId, increaseQuantity));
    }
}
