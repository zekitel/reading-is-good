package com.casestudy.readingisgood.service;


import com.casestudy.readingisgood.dto.CustomerOrdersRequestDTO;
import com.casestudy.readingisgood.dto.MonthlyStatisticDTO;
import com.casestudy.readingisgood.dto.OrderDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class StatisticServiceTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private StatisticService statisticService;

    @Test
    void monthlyOrderStatistics() {
        CustomerOrdersRequestDTO customerOrdersRequestDto = new CustomerOrdersRequestDTO();
        customerOrdersRequestDto.setCustomerId(1L);
        customerOrdersRequestDto.setPageSize(5);
        customerOrdersRequestDto.setPageNumber(0);

        List<MonthlyStatisticDTO> monthlyStatisticDTOS = statisticService.monthlyOrder(customerOrdersRequestDto.getCustomerId());
        List<OrderDTO> orderDTOS = orderService.listByCustomer(customerOrdersRequestDto);

        BigDecimal orderAmount = orderDTOS.stream().map(OrderDTO::getOrderAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        Long bookCount = orderDTOS.stream().map(OrderDTO::getBookCount).reduce(0L, Long::sum);


        assertThat(monthlyStatisticDTOS.get(0).getTotalOrderCount()).isEqualTo(orderDTOS.size());
        assertThat(monthlyStatisticDTOS.get(0).getTotalBookCount()).isEqualTo(bookCount);
        assertThat(monthlyStatisticDTOS.get(0).getTotalPurchasedAmount()).isEqualTo(orderAmount);

    }
}
