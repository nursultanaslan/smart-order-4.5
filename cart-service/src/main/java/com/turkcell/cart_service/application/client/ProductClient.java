package com.turkcell.cart_service.application.client;

import com.turkcell.cart_service.application.dto.response.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "product-service", path = "/api/v1/products")
public interface ProductClient {

    //product-service'ten stok ve ürün fiyat bilgisini alacagım.
    @GetMapping("/{id}")
    ProductResponse getProductById(@PathVariable("id") UUID productId);
}
