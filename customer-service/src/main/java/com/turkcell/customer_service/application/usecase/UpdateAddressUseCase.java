package com.turkcell.customer_service.application.usecase;

import com.turkcell.customer_service.domain.port.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateAddressUseCase {

    private final CustomerRepository customerRepository;

    public UpdateAddressUseCase(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


}
