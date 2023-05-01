package com.casestudy.readingisgood.service;

import com.casestudy.readingisgood.dto.MonthlyStatisticDTO;
import com.casestudy.readingisgood.entity.Order;

import java.util.List;

public interface StatisticService {
    List<MonthlyStatisticDTO> monthlyOrder(Long customerId);

    void persist(Order order);
}
