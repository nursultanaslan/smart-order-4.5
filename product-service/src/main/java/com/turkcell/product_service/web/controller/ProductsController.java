package com.turkcell.product_service.web.controller;

import com.turkcell.product_service.application.product.command.CreateProductCommand;
import com.turkcell.product_service.application.product.command.DecreaseProductStockCommand;
import com.turkcell.product_service.application.product.command.DeleteProductCommand;
import com.turkcell.product_service.application.product.command.UpdateProductCommand;
import com.turkcell.product_service.application.product.dto.DeletedProductResponse;
import com.turkcell.product_service.application.product.dto.PageableProductResponse;
import com.turkcell.product_service.application.product.dto.ProductResponse;
import com.turkcell.product_service.application.product.query.FindProductsByNameQuery;
import com.turkcell.product_service.application.product.query.GetProductByIdQuery;
import com.turkcell.product_service.core.cqrs.CommandHandler;
import com.turkcell.product_service.core.cqrs.QueryHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/api/v1/products")
public class ProductsController {

    private final CommandHandler<CreateProductCommand, ProductResponse>  createProductCommandHandler;
    private final CommandHandler<DeleteProductCommand, DeletedProductResponse>  deleteProductCommandHandler;
    private final QueryHandler<GetProductByIdQuery, ProductResponse>  getProductByIdQueryHandler;
    private final QueryHandler<FindProductsByNameQuery, PageableProductResponse>  findProductsByNameQueryHandler;
    private final CommandHandler<UpdateProductCommand, ProductResponse>  updateProductCommandHandler;
    private final CommandHandler<DecreaseProductStockCommand, ProductResponse>  decreaseProductStockCommand;

    public ProductsController(CommandHandler<CreateProductCommand, ProductResponse> createProductCommandHandler, CommandHandler<DeleteProductCommand, DeletedProductResponse> deleteProductCommandHandler, QueryHandler<GetProductByIdQuery, ProductResponse> getProductByIdQueryHandler, QueryHandler<FindProductsByNameQuery, PageableProductResponse> findProductsByNameQueryHandler, CommandHandler<UpdateProductCommand, ProductResponse> updateProductCommandHandler, CommandHandler<DecreaseProductStockCommand, ProductResponse> decreaseProductStockCommand) {
        this.createProductCommandHandler = createProductCommandHandler;
        this.deleteProductCommandHandler = deleteProductCommandHandler;
        this.getProductByIdQueryHandler = getProductByIdQueryHandler;
        this.findProductsByNameQueryHandler = findProductsByNameQueryHandler;
        this.updateProductCommandHandler = updateProductCommandHandler;
        this.decreaseProductStockCommand = decreaseProductStockCommand;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@RequestBody CreateProductCommand command) {
        return createProductCommandHandler.handle(command);
    }

    @DeleteMapping("/{productId}")
    public DeletedProductResponse deleteProduct(@PathVariable UUID productId) {
        return  deleteProductCommandHandler.handle(new DeleteProductCommand(productId));
    }

    @GetMapping("/{productId}")
    public ProductResponse getProductById(@PathVariable UUID productId) {
        return getProductByIdQueryHandler.handle(new GetProductByIdQuery(productId));
    }

    @GetMapping()
    public PageableProductResponse findProductsByNameQuery(
            @RequestParam() String productName,
            @RequestParam(defaultValue = "0") Integer pageIndex,
            @RequestParam(defaultValue = "10") Integer pageSize ) {
        return findProductsByNameQueryHandler.handle(new FindProductsByNameQuery(productName, pageIndex, pageSize));
    }

    @PutMapping("/{productId}")
    public ProductResponse updateProduct(@PathVariable UUID productId, @RequestBody UpdateProductCommand command) {
        return updateProductCommandHandler.handle(command);
    }

    @PutMapping("/{productId}/stock/decrease")
    public ProductResponse decreaseStock(@PathVariable("productId") UUID productId, @RequestParam("decreaseQuantity") Integer decreaseQuantity) {
        return decreaseProductStockCommand.handle(new DecreaseProductStockCommand(productId, decreaseQuantity));
    }

}
