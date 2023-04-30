package com.casestudy.readingisgood.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDto {

    private Long id;
    @NotNull
    @Positive
    private Long customerId;
    @NotNull
    @Positive
    private Long bookId;
    @NotNull
    @Positive
    private Long bookCount;

    private Double orderAmount;

    private LocalDateTime orderTime;
}
