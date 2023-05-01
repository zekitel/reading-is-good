package com.casestudy.readingisgood.service;

import com.casestudy.readingisgood.dto.CustomerDTO;
import com.casestudy.readingisgood.entity.Customer;
import jakarta.transaction.Transactional;

import java.util.Optional;

public interface CustomerService {

    @Transactional
    CustomerDTO create(CustomerDTO customerDto);


    Optional<Customer> findCustomerById(Long customerId);
}
