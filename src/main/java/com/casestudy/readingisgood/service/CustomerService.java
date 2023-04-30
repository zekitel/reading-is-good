package com.casestudy.readingisgood.service;

import com.casestudy.readingisgood.dto.CustomerDto;
import com.casestudy.readingisgood.entity.Customer;
import jakarta.transaction.Transactional;

import java.util.Optional;

public interface CustomerService {

    @Transactional
    CustomerDto create(CustomerDto customerDto);


    Optional<Customer> findCustomerById(Long customerId);
}
