package com.turkcell.customer_service.application.usecase;

import com.turkcell.customer_service.application.dto.request.UpdatePersonalDetailsRequest;
import com.turkcell.customer_service.application.dto.response.UpdatedPersonalDetailsResponse;
import com.turkcell.customer_service.application.exception.CustomerNotFoundException;
import com.turkcell.customer_service.domain.model.Customer;
import com.turkcell.customer_service.domain.model.CustomerId;
import com.turkcell.customer_service.domain.port.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdatePersonalDetailsUseCase {

    private final CustomerRepository customerRepository;

    public UpdatePersonalDetailsUseCase(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public UpdatedPersonalDetailsResponse updateCustomer(UpdatePersonalDetailsRequest request) {

        CustomerId id = new CustomerId(request.customerId());

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found" + id));




    }
}
