package com.turkcell.order_service.application.client;

import com.turkcell.order_service.application.dto.response.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "product-service", path = "/api/v1/products")
public interface ProductClient {

    @PutMapping("/{productId}/stock/decrease")
    ProductResponse decreaseStock(@PathVariable("productId") UUID productId, @RequestParam("decreaseQuantity") Integer decreaseQuantity);

}
