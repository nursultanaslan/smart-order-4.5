package com.turkcell.customer_service.application.usecase;

import com.turkcell.customer_service.application.dto.request.DeleteCustomerRequest;
import com.turkcell.customer_service.application.dto.response.DeletedCustomerResponse;
import com.turkcell.customer_service.application.exception.CustomerNotFoundException;
import com.turkcell.customer_service.application.mapper.CustomerMapper;
import com.turkcell.customer_service.domain.model.Customer;
import com.turkcell.customer_service.domain.model.CustomerId;
import com.turkcell.customer_service.domain.port.CustomerRepository;
import org.springframework.stereotype.Service;


@Service
public class DeleteCustomerUseCase {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public DeleteCustomerUseCase(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public DeletedCustomerResponse deleteCustomer(DeleteCustomerRequest request) {
        Customer customer = customerRepository.findById(new CustomerId(request.customerId()))
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found!"));

        customerRepository.delete(customer);
        return customerMapper.toDeletedResponse(customer);
    }
}
