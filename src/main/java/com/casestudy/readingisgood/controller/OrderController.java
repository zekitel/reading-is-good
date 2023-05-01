package com.casestudy.readingisgood.controller;

import com.casestudy.readingisgood.dto.CustomerOrdersRequestDTO;
import com.casestudy.readingisgood.dto.OrderDTO;
import com.casestudy.readingisgood.dto.OrderRequestDTO;
import com.casestudy.readingisgood.dto.OrderTimeIntervalsRequestDTO;
import com.casestudy.readingisgood.exception.DbNotFoundException;
import com.casestudy.readingisgood.exception.StartDateIsGreaterThanEndDateException;
import com.casestudy.readingisgood.exception.StockIsNotSufficientException;
import com.casestudy.readingisgood.exception.StockValueChangedException;
import com.casestudy.readingisgood.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/order/")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @PostMapping("create")
    public ResponseEntity<OrderDTO> create(@RequestBody @Valid OrderRequestDTO orderRequestDto) throws DbNotFoundException, StockIsNotSufficientException, StockValueChangedException {
        return ResponseEntity.ok(orderService.create(orderRequestDto));
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderDTO> get(@PathVariable @Positive @NotNull Long id) throws DbNotFoundException {
        return ResponseEntity.ok(orderService.get(id));
    }

    @PostMapping("list-by-date-interval")
    public ResponseEntity<List<OrderDTO>> listByDateInterval(@RequestBody @Valid OrderTimeIntervalsRequestDTO orderTimeIntervalsRequestDto) throws StartDateIsGreaterThanEndDateException {
        return ResponseEntity.ok(orderService.listByDateInterval(orderTimeIntervalsRequestDto));
    }

    @PostMapping("list-by-customer")
    public ResponseEntity<List<OrderDTO>> listByCustomer(@RequestBody @Valid CustomerOrdersRequestDTO customerOrdersRequestDto) {
        return ResponseEntity.ok(orderService.listByCustomer(customerOrdersRequestDto));
    }

}

