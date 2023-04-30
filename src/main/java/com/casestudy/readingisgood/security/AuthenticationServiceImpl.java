package com.casestudy.readingisgood.security;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {


    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtTokenService jwtTokenService;



    @Override
    public JwtResponseDto authenticate(JwtRequestDto jwtRequestDto) {
        try {
            authenticationManager.
                    authenticate(new UsernamePasswordAuthenticationToken(jwtRequestDto.getUsername(), jwtRequestDto.getPassword()));
            final UserDetails userDetails = userDetailsService
                    .loadUserByUsername(jwtRequestDto.getUsername());

            final String token = jwtTokenService.generateToken(userDetails);
            return new JwtResponseDto(token);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", e);
        }
    }
}
