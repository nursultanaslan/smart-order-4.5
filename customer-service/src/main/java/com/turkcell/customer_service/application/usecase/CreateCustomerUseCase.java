package com.turkcell.customer_service.application.usecase;

import com.turkcell.customer_service.application.dto.request.CreateCustomerRequest;
import com.turkcell.customer_service.application.dto.response.CustomerResponse;
import com.turkcell.customer_service.application.mapper.CustomerMapper;
import com.turkcell.customer_service.domain.model.Customer;
import com.turkcell.customer_service.domain.port.CustomerRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class CreateCustomerUseCase {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CreateCustomerUseCase(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public CustomerResponse createCustomer(@Valid CreateCustomerRequest request) {
        Customer customer = customerMapper.toDomain(request);
        customer = customerRepository.save(customer);
        return customerMapper.toResponse(customer);
    }
}
