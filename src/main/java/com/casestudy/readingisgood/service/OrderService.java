package com.casestudy.readingisgood.service;

import com.casestudy.readingisgood.dto.CustomerOrdersRequestDTO;
import com.casestudy.readingisgood.dto.OrderDTO;
import com.casestudy.readingisgood.dto.OrderRequestDTO;
import com.casestudy.readingisgood.dto.OrderTimeIntervalsRequestDTO;
import com.casestudy.readingisgood.exception.DbNotFoundException;
import com.casestudy.readingisgood.exception.StartDateIsGreaterThanEndDateException;
import com.casestudy.readingisgood.exception.StockIsNotSufficientException;
import com.casestudy.readingisgood.exception.StockValueChangedException;
import jakarta.transaction.Transactional;

import java.util.List;

public interface OrderService {

    @Transactional
    OrderDTO create(OrderRequestDTO orderRequestDto) throws DbNotFoundException, StockIsNotSufficientException, StockValueChangedException;

    @Transactional
    OrderDTO get(Long id) throws DbNotFoundException;
    List<OrderDTO> listByDateInterval(OrderTimeIntervalsRequestDTO orderTimeIntervalsRequestDTO) throws StartDateIsGreaterThanEndDateException;

    List<OrderDTO> listByCustomer(CustomerOrdersRequestDTO customerOrdersRequestDto);
}
