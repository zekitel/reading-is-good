package com.casestudy.readingisgood.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerDTO {

    private Long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @Email
    @NotBlank
    private String email;
}
