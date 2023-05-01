package com.casestudy.readingisgood.controller;


import com.casestudy.readingisgood.dto.MonthlyStatisticDTO;
import com.casestudy.readingisgood.service.StatisticService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/statistic/")
@AllArgsConstructor
public class StatisticController {

    private final StatisticService statisticService;


    @GetMapping("monthly-order/{customerId}")
    public ResponseEntity<List<MonthlyStatisticDTO>> monthlyOrder(@PathVariable @NotNull @Positive Long customerId) {
        return ResponseEntity.ok(statisticService.monthlyOrder(customerId));
    }

}
