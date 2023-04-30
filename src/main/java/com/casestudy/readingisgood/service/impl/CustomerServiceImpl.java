package com.casestudy.readingisgood.service.impl;

import com.casestudy.readingisgood.dto.CustomerDto;
import com.casestudy.readingisgood.entity.Customer;
import com.casestudy.readingisgood.repository.CustomerRepository;
import com.casestudy.readingisgood.service.CustomerService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {


    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;




    @Override
    @Transactional
    public CustomerDto create(CustomerDto customerDto) {
        log.info("Customer create started. Coming data: . Customerdto:{}", customerDto);
        Customer customer = modelMapper.map(customerDto, Customer.class);
        Customer result = customerRepository.save(customer);
        log.info("Customer create finished. result data: . Customer: {}", result);
        return CustomerDto.builder()
                .firstName(result.getFirstName())
                .lastName(result.getLastName())
                .email(result.getEmail())
                .id(result.getId())
                .build();
    }

    @Override
    public Optional<Customer> findCustomerById(Long customerId) {
        return customerRepository.findById(customerId);
    }
}
