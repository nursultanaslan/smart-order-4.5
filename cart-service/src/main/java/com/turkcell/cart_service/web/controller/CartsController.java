package com.turkcell.cart_service.web.controller;

import com.turkcell.cart_service.application.usecase.AddToCartUseCase;
import com.turkcell.cart_service.application.usecase.DeleteProductUseCase;
import com.turkcell.cart_service.application.usecase.GetCartCustomerByIdUseCase;
import com.turkcell.cart_service.domain.model.Cart;
import com.turkcell.cart_service.web.dto.request.CartItemRequest;
import com.turkcell.cart_service.web.dto.request.UpdateQuantityRequest;
import com.turkcell.cart_service.web.dto.response.CartItemResponse;
import com.turkcell.cart_service.web.dto.response.CartResponse;
import com.turkcell.cart_service.web.dto.response.DeletedCartItemResponse;
import com.turkcell.cart_service.web.mapper.CartMapper;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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

    @PostMapping()
    public CartItemResponse addToCart(@Valid @RequestBody CartItemRequest request,
                                      @AuthenticationPrincipal Jwt jwt) {

        UUID customerId = UUID.fromString(jwt.getSubject());

        Cart cart = addToCartUseCase.addToCart(
                customerId,
                request.productId(),
                request.quantity()
        );

        return cartMapper.toCartItemResponse(cart, request.productId());
    }

    @GetMapping("/{productId}")
    public CartResponse getCart(@PathVariable UUID productId,
                                @AuthenticationPrincipal Jwt jwt) {
        UUID customerId = UUID.fromString(jwt.getSubject());
        Cart cart = getCartCustomerByIdUseCase.getCart(customerId);

        return cartMapper.toCartResponse(cart);
    }

    @DeleteMapping("/items/{productId}")
    public DeletedCartItemResponse deleteFromCart(@PathVariable UUID productId, @AuthenticationPrincipal Jwt jwt) {
        UUID customerId = UUID.fromString(jwt.getSubject());
        Cart cart = deleteProductUseCase.deleteCartItem(customerId, productId);

        return new DeletedCartItemResponse(productId);

    }

    @PatchMapping()
    public CartItemResponse updateCartItem(@RequestBody UpdateQuantityRequest request, @AuthenticationPrincipal Jwt jwt) {
        return null;
    }

}
