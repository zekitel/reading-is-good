package com.casestudy.readingisgood.controller;

import com.casestudy.readingisgood.dto.CustomerOrdersRequestDto;
import com.casestudy.readingisgood.dto.OrderDto;
import com.casestudy.readingisgood.dto.OrderTimeIntervalsRequestDto;
import com.casestudy.readingisgood.dto.OrderRequestDto;
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
    public ResponseEntity<OrderDto> create(@RequestBody @Valid OrderRequestDto orderRequestDto) {
        return ResponseEntity.ok(orderService.create(orderRequestDto));
    }

    @GetMapping("get")
    public ResponseEntity<OrderDto> get(@RequestParam  @Positive @NotNull Long orderId) {
        return ResponseEntity.ok(orderService.get(orderId));
    }

    @PostMapping("listOrdersByDateInterval")
    public ResponseEntity<List<OrderDto>> listOrdersByDateInterval(@RequestBody @Valid OrderTimeIntervalsRequestDto orderTimeIntervalsRequestDto) {
        return ResponseEntity.ok(orderService.listOrdersByDateInterval(orderTimeIntervalsRequestDto.getStartTimeStamp(), orderTimeIntervalsRequestDto.getEndTimeStamp(), orderTimeIntervalsRequestDto.getPageNumber(), orderTimeIntervalsRequestDto.getPageSize()));
    }

    @PostMapping("listOrderByCustomer")
    public ResponseEntity<List<OrderDto>> listOrderByCustomer(@RequestBody @Valid CustomerOrdersRequestDto customerOrdersRequestDto) {
        return ResponseEntity.ok(orderService.listOrderByCustomer(customerOrdersRequestDto));
    }

}

