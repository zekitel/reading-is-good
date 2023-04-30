package com.casestudy.readingisgood.security;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequestDto implements Serializable {

	@Serial
	private static final long serialVersionUID = 5926468583005150707L;

	@NotBlank
	private String username;
	@NotBlank
	private String password;
}