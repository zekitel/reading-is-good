package com.casestudy.readingisgood.controller;


import com.casestudy.readingisgood.dto.CustomerDto;
import com.casestudy.readingisgood.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/customer/")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;


    @PostMapping("save")
    public ResponseEntity<CustomerDto> persistNewCustomer(@RequestBody @Valid CustomerDto customerDto) {
        return ResponseEntity.ok(customerService.create(customerDto));
    }

}
