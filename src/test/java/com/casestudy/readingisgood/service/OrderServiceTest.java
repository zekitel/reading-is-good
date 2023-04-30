package com.casestudy.readingisgood.service;

import com.casestudy.readingisgood.dto.CustomerOrdersRequestDto;
import com.casestudy.readingisgood.dto.MonthlyStatisticDto;
import com.casestudy.readingisgood.dto.OrderDto;
import com.casestudy.readingisgood.dto.OrderRequestDto;
import com.casestudy.readingisgood.entity.Order;
import com.casestudy.readingisgood.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.ZoneOffset;
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
    void create() {
        List<Order> all = orderRepository.findAll();
        assertThat(all).hasSize(4);

        OrderRequestDto order1 = OrderRequestDto.builder()
                .bookId(1L)
                .customerId(1L)
                .bookCount(3L)
                .build();

        OrderDto orderDto = orderService.create(order1);

        assertThat(orderDto.getId()).isNotNull();

        List<Order> all1 = orderRepository.findAll();
        assertThat(all1).hasSize(5);
        assertThat(all1.get(4).getBookId()).isEqualTo(order1.getBookId());
        assertThat(all1.get(4).getCustomerId()).isEqualTo(order1.getCustomerId());
        assertThat(all1.get(4).getBookCount()).isEqualTo(order1.getBookCount());


    }

    @Test
    void get() {

        List<Order> all = orderRepository.findAll();
        assertThat(all.get(0).getId()).isEqualTo(1);


        OrderDto orderDto = orderService.get(all.get(0).getId());

        assertThat(orderDto.getOrderAmount()).isEqualTo(all.get(0).getOrderAmount());
        assertThat(orderDto.getOrderTime()).isEqualTo(all.get(0).getOrderTime());
        assertThat(orderDto.getBookId()).isEqualTo(all.get(0).getBookId());
    }

    @Test
    void listOrdersByDateInterval() {
        List<Order> all = orderRepository.findAll();
        long l = all.get(1).getOrderTime().toEpochSecond(ZoneOffset.UTC);
        List<OrderDto> orderDtos = orderService.listOrdersByDateInterval(l - 10, l - 9, 0, 5);

        assertThat(orderDtos).isEmpty();

        List<OrderDto> orderDtos2 = orderService.listOrdersByDateInterval(l - 1, l + 10, 0, 5);
        assertThat(orderDtos2).hasSize(4);
    }

    @Test
    void monthlyOrderStatistics() {
        CustomerOrdersRequestDto customerOrdersRequestDto = new CustomerOrdersRequestDto();
        customerOrdersRequestDto.setCustomerId(1L);
        customerOrdersRequestDto.setPageSize(5);
        customerOrdersRequestDto.setPageNumber(0);

        List<MonthlyStatisticDto> monthlyStatisticDtos = orderService.monthlyOrderStatistics(customerOrdersRequestDto.getCustomerId());
        List<OrderDto> orderDtos = orderService.listOrderByCustomer(customerOrdersRequestDto);

        Double orderAmount = orderDtos.stream().map(OrderDto::getOrderAmount).reduce(0.0, Double::sum);
        Long bookCount = orderDtos.stream().map(OrderDto::getBookCount).reduce(0L, Long::sum);


        assertThat(monthlyStatisticDtos.get(0).getTotalOrderCount()).isEqualTo(orderDtos.size());
        assertThat(monthlyStatisticDtos.get(0).getTotalBookCount()).isEqualTo(bookCount);
        assertThat(monthlyStatisticDtos.get(0).getTotalPurchasedAmount()).isEqualTo(orderAmount);

    }

    @Test
    void listOrderByCustomer() {

        CustomerOrdersRequestDto customerOrdersRequestDto = new CustomerOrdersRequestDto();
        customerOrdersRequestDto.setCustomerId(2L);
        customerOrdersRequestDto.setPageSize(5);
        customerOrdersRequestDto.setPageNumber(0);

        List<Order> all = orderRepository.findAll();
        List<OrderDto> orderDtos = orderService.listOrderByCustomer(customerOrdersRequestDto);
        assertThat(orderDtos).hasSize(2);

        List<Order> orders = all.stream().filter(order -> order.getCustomerId().equals(customerOrdersRequestDto.getCustomerId())).toList();
        assertThat(orders.size()).isEqualTo(orderDtos.size());
    }
}