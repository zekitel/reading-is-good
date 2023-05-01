package com.casestudy.readingisgood.service;

import com.casestudy.readingisgood.dto.CustomerOrdersRequestDTO;
import com.casestudy.readingisgood.dto.OrderDTO;
import com.casestudy.readingisgood.dto.OrderRequestDTO;
import com.casestudy.readingisgood.dto.OrderTimeIntervalsRequestDTO;
import com.casestudy.readingisgood.entity.Order;
import com.casestudy.readingisgood.exception.DbNotFoundException;
import com.casestudy.readingisgood.exception.StartDateIsGreaterThanEndDateException;
import com.casestudy.readingisgood.exception.StockIsNotSufficientException;
import com.casestudy.readingisgood.exception.StockValueChangedException;
import com.casestudy.readingisgood.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class OrderServiceTest {


    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    @Test
    void create() throws DbNotFoundException, StockIsNotSufficientException, StockValueChangedException {
        List<Order> all = orderRepository.findAll();
        assertThat(all).hasSize(4);

        OrderRequestDTO order1 = OrderRequestDTO.builder()
                .bookId(1L)
                .customerId(1L)
                .bookCount(2L)
                .build();

        OrderDTO orderDto = orderService.create(order1);

        assertThat(orderDto.getId()).isNotNull();

        List<Order> all1 = orderRepository.findAll();
        assertThat(all1).hasSize(5);
        assertThat(all1.get(4).getBookId()).isEqualTo(order1.getBookId());
        assertThat(all1.get(4).getCustomerId()).isEqualTo(order1.getCustomerId());
        assertThat(all1.get(4).getBookCount()).isEqualTo(order1.getBookCount());


    }

    @Test
    void get() throws DbNotFoundException {

        List<Order> all = orderRepository.findAll();
        assertThat(all.get(0).getId()).isEqualTo(1);


        OrderDTO orderDto = orderService.get(all.get(0).getId());

        assertThat(orderDto.getOrderAmount()).isEqualTo(all.get(0).getOrderAmount());
        assertThat(orderDto.getOrderTime()).isEqualTo(all.get(0).getOrderTime());
        assertThat(orderDto.getBookId()).isEqualTo(all.get(0).getBookId());
    }

    @Test
    void listOrdersByDateInterval() throws StartDateIsGreaterThanEndDateException {

        OrderTimeIntervalsRequestDTO orderTimeIntervalsRequestDTO = new OrderTimeIntervalsRequestDTO();
        orderTimeIntervalsRequestDTO.setStartDateTime(LocalDateTime.now().minus(10, ChronoUnit.MINUTES));
        orderTimeIntervalsRequestDTO.setEndDateTime(LocalDateTime.now().minus(9, ChronoUnit.MINUTES));
        orderTimeIntervalsRequestDTO.setPageNumber(0);
        orderTimeIntervalsRequestDTO.setPageSize(5);


        List<OrderDTO> orderDTOS = orderService.listByDateInterval(orderTimeIntervalsRequestDTO);

        assertThat(orderDTOS).isEmpty();


        orderTimeIntervalsRequestDTO = new OrderTimeIntervalsRequestDTO();
        orderTimeIntervalsRequestDTO.setStartDateTime(LocalDateTime.now().minus(1, ChronoUnit.MINUTES));
        orderTimeIntervalsRequestDTO.setEndDateTime(LocalDateTime.now().plus(10, ChronoUnit.MINUTES));
        orderTimeIntervalsRequestDTO.setPageNumber(0);
        orderTimeIntervalsRequestDTO.setPageSize(5);

        orderDTOS = orderService.listByDateInterval(orderTimeIntervalsRequestDTO);
        assertThat(orderDTOS).hasSize(4);
    }


    @Test
    void listOrderByCustomer() {

        CustomerOrdersRequestDTO customerOrdersRequestDto = new CustomerOrdersRequestDTO();
        customerOrdersRequestDto.setCustomerId(2L);
        customerOrdersRequestDto.setPageSize(5);
        customerOrdersRequestDto.setPageNumber(0);

        List<Order> all = orderRepository.findAll();
        List<OrderDTO> orderDTOS = orderService.listByCustomer(customerOrdersRequestDto);
        assertThat(orderDTOS).hasSize(2);

        List<Order> orders = all.stream().filter(order -> order.getCustomerId().equals(customerOrdersRequestDto.getCustomerId())).toList();
        assertThat(orders).hasSameSizeAs(orderDTOS);
    }
}