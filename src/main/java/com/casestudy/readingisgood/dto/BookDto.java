package com.casestudy.readingisgood.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookDto {


    private Long id;
    @NotNull
    private String title;
    @NotNull
    private String author;

    @NotNull
    @Positive
    private Double price;

    @NotNull
    private String isbn;

    @NotNull
    @PositiveOrZero
    private Long stock;
}
