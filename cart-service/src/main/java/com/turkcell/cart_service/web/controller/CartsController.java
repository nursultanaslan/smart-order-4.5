package com.turkcell.cart_service.web.controller;

import com.turkcell.cart_service.application.usecase.AddToCartUseCase;
import com.turkcell.cart_service.application.usecase.DeleteProductUseCase;
import com.turkcell.cart_service.application.usecase.GetCartCustomerByIdUseCase;
import com.turkcell.cart_service.domain.model.Cart;
import com.turkcell.cart_service.web.dto.request.CartItemRequest;
import com.turkcell.cart_service.web.dto.response.CartItemResponse;
import com.turkcell.cart_service.web.dto.response.CartResponse;
import com.turkcell.cart_service.web.dto.response.DeletedCartItemResponse;
import com.turkcell.cart_service.web.mapper.CartMapper;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController("/api/v1/carts")
@Validated
public class CartsController{

    private final AddToCartUseCase addToCartUseCase;
    private final GetCartCustomerByIdUseCase getCartCustomerByIdUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final CartMapper cartMapper;

    public CartsController(AddToCartUseCase addToCartUseCase, GetCartCustomerByIdUseCase getCartCustomerByIdUseCase, DeleteProductUseCase deleteProductUseCase, CartMapper cartMapper) {
        this.addToCartUseCase = addToCartUseCase;
        this.getCartCustomerByIdUseCase = getCartCustomerByIdUseCase;
        this.deleteProductUseCase = deleteProductUseCase;
        this.cartMapper = cartMapper;
    }

    @PostMapping("/{customerId}")
    public CartItemResponse addToCart(@PathVariable UUID customerId, @Valid @RequestBody CartItemRequest request) {

        Cart cart = addToCartUseCase.addToCart(
                customerId,
                request.productId(),
                request.quantity()
        );

        return cartMapper.toCartItemResponse(cart, request.productId());
    }

    @GetMapping("/{customerId}")
    public CartResponse getCart(@PathVariable UUID customerId, @RequestParam UUID productId  ) {
        Cart cart = getCartCustomerByIdUseCase.getCart(customerId);

        return cartMapper.toCartResponse(cart);
    }

    @DeleteMapping("/items/{customerId}/{productId}")
    public DeletedCartItemResponse deleteFromCart(@PathVariable UUID customerId, @PathVariable UUID productId) {
        Cart cart = deleteProductUseCase.deleteCartItem(customerId, productId);

        return new DeletedCartItemResponse(productId);

    }


}
