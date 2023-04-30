package com.casestudy.readingisgood.service;

import com.casestudy.readingisgood.dto.CustomerOrdersRequestDto;
import com.casestudy.readingisgood.dto.MonthlyStatisticDto;
import com.casestudy.readingisgood.dto.OrderDto;
import com.casestudy.readingisgood.dto.OrderRequestDto;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;

import java.util.List;

public interface OrderService {

    @SneakyThrows
    @Transactional
    OrderDto create(OrderRequestDto orderRequestDto);

    @Transactional
    OrderDto get(Long id);

    @SneakyThrows
    List<OrderDto> listOrdersByDateInterval(long startDate, long endDate, int pageNo, int pageSize);

    List<MonthlyStatisticDto> monthlyOrderStatistics(Long customerId);

    List<OrderDto> listOrderByCustomer(CustomerOrdersRequestDto customerOrdersRequestDto);
}
