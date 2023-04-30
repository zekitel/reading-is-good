package com.casestudy.readingisgood.controller;


import com.casestudy.readingisgood.dto.MonthlyStatisticDto;
import com.casestudy.readingisgood.service.OrderService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/statistic/")
@AllArgsConstructor
public class StatisticController {

    private final OrderService orderService;


    @GetMapping("monthlyOrderStatistics")
    public ResponseEntity<List<MonthlyStatisticDto>> monthlyOrderStatistics(@RequestParam @NotNull @Positive Long customerId) {
        return ResponseEntity.ok(orderService.monthlyOrderStatistics(customerId));
    }

}
