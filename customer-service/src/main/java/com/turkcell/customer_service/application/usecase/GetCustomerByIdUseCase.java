package com.turkcell.customer_service.application.usecase;

import com.turkcell.customer_service.application.dto.response.CustomerResponse;
import com.turkcell.customer_service.application.dto.response.GetCustomerByIdResponse;
import com.turkcell.customer_service.application.exception.CustomerNotFoundException;
import com.turkcell.customer_service.application.mapper.CustomerMapper;
import com.turkcell.customer_service.domain.model.Customer;
import com.turkcell.customer_service.domain.model.CustomerId;
import com.turkcell.customer_service.domain.port.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetCustomerByIdUseCase {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public GetCustomerByIdUseCase(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public GetCustomerByIdResponse getCustomerById(UUID customerId) {
        Customer customer = customerRepository.findById(new CustomerId(customerId))
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found!"));

        return customerMapper.toGetByIdResponse(customer);
    }
}
