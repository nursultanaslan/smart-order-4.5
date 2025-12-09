package com.turkcell.customer_service.application.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "order-service", path = "/api/v1/orders")
public interface OrderClient {


}
