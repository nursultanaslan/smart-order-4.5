package com.turkcell.customer_service.web;

import com.turkcell.customer_service.application.dto.request.CreateCustomerRequest;
import com.turkcell.customer_service.application.dto.request.DeleteCustomerRequest;
import com.turkcell.customer_service.application.dto.response.CustomerResponse;
import com.turkcell.customer_service.application.dto.response.DeletedCustomerResponse;
import com.turkcell.customer_service.application.dto.response.GetCustomerByIdResponse;
import com.turkcell.customer_service.application.usecase.CreateCustomerUseCase;
import com.turkcell.customer_service.application.usecase.DeleteCustomerUseCase;
import com.turkcell.customer_service.application.usecase.GetCustomerByIdUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomersController {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final DeleteCustomerUseCase deleteCustomerUseCase;
    private final GetCustomerByIdUseCase getCustomerByIdUseCase;

    public CustomersController(CreateCustomerUseCase createCustomerUseCase, DeleteCustomerUseCase deleteCustomerUseCase, GetCustomerByIdUseCase getCustomerByIdUseCase) {
        this.createCustomerUseCase = createCustomerUseCase;
        this.deleteCustomerUseCase = deleteCustomerUseCase;
        this.getCustomerByIdUseCase = getCustomerByIdUseCase;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse create(@RequestBody @Valid CreateCustomerRequest request) {
        return createCustomerUseCase.createCustomer(request);
    }

    @DeleteMapping("/{id}")
    public DeletedCustomerResponse delete(@PathVariable("id") UUID customerId) {
        return deleteCustomerUseCase.deleteCustomer(new DeleteCustomerRequest(customerId));
    }

    @GetMapping("/{id}")
    public GetCustomerByIdResponse getById(@PathVariable("id") UUID customerId) {
        return getCustomerByIdUseCase.getCustomerById(customerId);
    }
}
