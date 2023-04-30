package com.casestudy.readingisgood.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class CustomerOrdersRequestDto {

    @Positive
    @NotNull
    private Long customerId;
    @Positive
    @NotNull
    private int pageSize;
    @PositiveOrZero
    @NotNull
    private int pageNumber;
}
