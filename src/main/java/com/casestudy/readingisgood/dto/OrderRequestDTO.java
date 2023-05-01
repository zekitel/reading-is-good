package com.casestudy.readingisgood.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderRequestDTO {

    @NotNull
    @Positive
    private Long customerId;
    @NotNull
    @Positive
    private Long bookId;
    @NotNull
    @Positive
    private Long bookCount;
}
