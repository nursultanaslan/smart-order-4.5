package com.turkcell.customer_service.application.usecase;

import com.turkcell.customer_service.application.dto.request.UpdateAddressRequest;
import com.turkcell.customer_service.application.dto.response.UpdatedAddressResponse;
import com.turkcell.customer_service.application.exception.CustomerNotFoundException;
import com.turkcell.customer_service.application.mapper.CustomerMapper;
import com.turkcell.customer_service.domain.model.Address;
import com.turkcell.customer_service.domain.model.Customer;
import com.turkcell.customer_service.domain.model.CustomerId;
import com.turkcell.customer_service.domain.port.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateAddressUseCase {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public UpdateAddressUseCase(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public UpdatedAddressResponse updateAddress(UpdateAddressRequest request) {
        Customer customer = customerRepository.findById(new CustomerId(request.customerId()))
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found!"));

        Address address = new Address(
                request.country(),
                request.city(),
                request.street(),
                request.postalCode(),
                request.houseNumber()
        );

        customer.updateAddress(address);
        return customerMapper.toUpdatedResponse(customer);
    }

}
