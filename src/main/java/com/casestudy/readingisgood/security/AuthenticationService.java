package com.casestudy.readingisgood.security;

public interface AuthenticationService {
    JwtResponseDto authenticate(JwtRequestDto jwtRequestDto);
}
