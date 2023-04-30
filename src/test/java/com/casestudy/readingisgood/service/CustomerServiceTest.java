package com.casestudy.readingisgood.service;

import com.casestudy.readingisgood.dto.CustomerDto;
import com.casestudy.readingisgood.entity.Customer;
import com.casestudy.readingisgood.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CustomerServiceTest {


    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void create() {
        List<Customer> all = customerRepository.findAll();
        assertThat(all).hasSize(2);

        CustomerDto customerDto1 = CustomerDto.builder()
                .email("test_mail@gmail.com")
                .firstName("test_name")
                .lastName("test_last_name")
                .build();

        CustomerDto customerDto = customerService.create(customerDto1);
        assertThat(customerDto.getId()).isNotNull();

        all = customerRepository.findAll();
        assertThat(all).hasSize(3);

        assertThat(all.get(2).getEmail()).isEqualTo(customerDto1.getEmail());


    }

    @Test
    void findCustomerById() {

        List<Customer> all = customerRepository.findAll();
        assertThat(all.get(0).getId()).isEqualTo(1);


        Customer customer = customerService.findCustomerById(all.get(0).getId()).orElseThrow();

        assertThat(customer.getEmail()).isEqualTo(all.get(0).getEmail());
        assertThat(customer.getFirstName()).isEqualTo(all.get(0).getFirstName());
        assertThat(customer.getLastName()).isEqualTo(all.get(0).getLastName());
    }
}