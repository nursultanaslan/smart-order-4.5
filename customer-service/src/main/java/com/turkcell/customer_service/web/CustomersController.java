package com.turkcell.customer_service.web;

import com.turkcell.customer_service.application.dto.request.CreateCustomerRequest;
import com.turkcell.customer_service.application.dto.response.CustomerResponse;
import com.turkcell.customer_service.application.usecase.CreateCustomerUseCase;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomersController {

    private final CreateCustomerUseCase createCustomerUseCase;

    public CustomersController(CreateCustomerUseCase createCustomerUseCase) {
        this.createCustomerUseCase = createCustomerUseCase;
    }

    @PostMapping()
    public CustomerResponse create(@RequestBody @Valid CreateCustomerRequest request) {
        return createCustomerUseCase.createCustomer(request);
    }
}
