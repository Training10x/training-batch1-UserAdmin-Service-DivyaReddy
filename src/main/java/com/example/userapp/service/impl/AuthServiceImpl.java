package com.example.userapp.service.impl;

import com.example.userapp.dto.AuthRequest;
import com.example.userapp.dto.AuthResponse;
import com.example.userapp.exception.InvalidCredentialsException;
import com.example.userapp.jwt.JwtUtil;
import com.example.userapp.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AuthResponse verify(AuthRequest authRequest){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));


            String username = authentication.getName();
            String role = authentication.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");

            String token = jwtUtil.generateToken(username, role);

            return new AuthResponse(token);
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException("Invalid credentials. Please try again with a valid username and password.");
        }
    }
}
