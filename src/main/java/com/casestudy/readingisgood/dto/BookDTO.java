package com.casestudy.readingisgood.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookDTO {


    private Long id;
    @NotNull
    private String title;
    @NotNull
    private String author;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotBlank
    @Size(max = 50)
    private String isbn;

    @NotNull
    @PositiveOrZero
    private Long stock;
}
