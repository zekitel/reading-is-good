package com.casestudy.readingisgood.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyStatisticDto {
    private String month;
    private Long totalOrderCount;
    private Long totalBookCount;
    private Double totalPurchasedAmount;
}
