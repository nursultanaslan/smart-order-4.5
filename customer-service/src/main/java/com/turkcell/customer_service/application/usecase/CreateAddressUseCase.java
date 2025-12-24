package com.turkcell.customer_service.application.usecase;

import com.turkcell.customer_service.application.dto.request.CreateAddressRequest;
import com.turkcell.customer_service.application.dto.response.CreatedAddressResponse;
import com.turkcell.customer_service.application.exception.CustomerNotFoundException;
import com.turkcell.customer_service.application.mapper.CustomerMapper;
import com.turkcell.customer_service.domain.model.Address;
import com.turkcell.customer_service.domain.model.Customer;
import com.turkcell.customer_service.domain.model.CustomerId;
import com.turkcell.customer_service.domain.port.CustomerRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Service
@Validated
public class CreateAddressUseCase {

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    public CreateAddressUseCase(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    public CreatedAddressResponse createAddress(UUID customerId, @Valid CreateAddressRequest request) {
        Customer customer = customerRepository.findById(new CustomerId(customerId))
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found!"));

        Address address = new Address(
                request.country(),
                request.city(),
                request.street(),
                request.postalCode(),
                request.houseNumber()
        );

        customer.addNewAddress(address);
        customerRepository.save(customer);

        return new CreatedAddressResponse(
                address.country(),
                address.city(),
                address.street(),
                address.postalCode(),
                address.houseNumber()
        );
    }
}
