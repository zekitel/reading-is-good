package com.casestudy.readingisgood.security;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JwtResponseDto implements Serializable {

	@Serial
	private static final long serialVersionUID = -8091879091924046844L;
	private String jwttoken;
}