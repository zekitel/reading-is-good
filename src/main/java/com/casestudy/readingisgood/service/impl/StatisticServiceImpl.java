package com.casestudy.readingisgood.service.impl;

import com.casestudy.readingisgood.dto.MonthlyStatisticDTO;
import com.casestudy.readingisgood.entity.Order;
import com.casestudy.readingisgood.entity.OrderStatistic;
import com.casestudy.readingisgood.repository.OrderStatisticRepository;
import com.casestudy.readingisgood.service.StatisticService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
@Slf4j
public class StatisticServiceImpl implements StatisticService {


    private final OrderStatisticRepository orderStatisticRepository;


    @Override
    public List<MonthlyStatisticDTO> monthlyOrder(Long customerId) {
        return orderStatisticRepository.monthlyStatistics(customerId);
    }


    @Override
    public void persist(Order order){

        OrderStatistic orderStatistic = OrderStatistic.builder()
                .orderMonth(order.getOrderTime().getYear() + "-" + order.getOrderTime().getMonth())
                .totalBookCount(order.getBookCount())
                .totalPurchasedAmount(order.getOrderAmount())
                .customerId(order.getCustomerId())
                .build();

        OrderStatistic savedOrderStatistic = orderStatisticRepository.save(orderStatistic);

        log.info("Order statistic persisted. OrderStatistic:{}", savedOrderStatistic);
    }
}
