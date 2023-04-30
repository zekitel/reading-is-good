package com.casestudy.readingisgood.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtTokenService {

    boolean validateToken(String token, UserDetails userDetails);

    String generateToken(UserDetails userDetails);

    String getUsernameFromToken(String token);
}