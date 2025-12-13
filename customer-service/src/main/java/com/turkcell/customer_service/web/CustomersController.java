package com.turkcell.customer_service.web;

import com.turkcell.customer_service.application.dto.request.CreateCustomerRequest;
import com.turkcell.customer_service.application.dto.request.DeleteCustomerRequest;
import com.turkcell.customer_service.application.dto.request.UpdateAddressRequest;
import com.turkcell.customer_service.application.dto.request.UpdatePersonalDetailsRequest;
import com.turkcell.customer_service.application.dto.response.*;
import com.turkcell.customer_service.application.usecase.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomersController {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final DeleteCustomerUseCase deleteCustomerUseCase;
    private final GetCustomerByIdUseCase getCustomerByIdUseCase;
    private final UpdatePersonalDetailsUseCase updatePersonalDetailsUseCase;
    private final UpdateAddressUseCase updateAddressUseCase;

    public CustomersController(CreateCustomerUseCase createCustomerUseCase, DeleteCustomerUseCase deleteCustomerUseCase,
            GetCustomerByIdUseCase getCustomerByIdUseCase, UpdatePersonalDetailsUseCase updatePersonalDetailsUseCase,
            UpdateAddressUseCase updateAddressUseCase) {
        this.createCustomerUseCase = createCustomerUseCase;
        this.deleteCustomerUseCase = deleteCustomerUseCase;
        this.getCustomerByIdUseCase = getCustomerByIdUseCase;
        this.updatePersonalDetailsUseCase = updatePersonalDetailsUseCase;
        this.updateAddressUseCase = updateAddressUseCase;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse create(@RequestBody @Valid CreateCustomerRequest request) {
        return createCustomerUseCase.createCustomer(request);
    }

    @DeleteMapping("/delete/{id}")
    public DeletedCustomerResponse delete(@PathVariable("id") UUID customerId) {
        return deleteCustomerUseCase.deleteCustomer(new DeleteCustomerRequest(customerId));
    }

    @GetMapping("/{id}")
    public GetCustomerByIdResponse getById(@PathVariable("id") UUID customerId) {
        return getCustomerByIdUseCase.getCustomerById(customerId);
    }

    @PutMapping("/info/{id}")
    public UpdatedPersonalDetailsResponse updatePersonalDetails(@PathVariable("id") UUID customerId,
            @RequestBody UpdatePersonalDetailsRequest request) {
        UpdatePersonalDetailsRequest finalRequest = new UpdatePersonalDetailsRequest(
                customerId,
                request.firstName(),
                request.lastName(),
                request.email(),
                request.phone());
        return updatePersonalDetailsUseCase.updateCustomer(finalRequest);
    }

    @PutMapping("/address/{id}")
    public UpdatedAddressResponse updateAddress(@PathVariable("id") UUID customerId,
            @RequestBody UpdateAddressRequest request) {
        UpdateAddressRequest finalRequest = new UpdateAddressRequest(
                customerId,
                request.country(),
                request.city(),
                request.street(),
                request.postalCode(),
                request.houseNumber());

        return updateAddressUseCase.updateAddress(finalRequest);
    }
}
