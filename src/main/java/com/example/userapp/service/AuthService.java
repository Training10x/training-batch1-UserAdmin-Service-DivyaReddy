package com.example.userapp.service;

import com.example.userapp.dto.AuthRequest;
import com.example.userapp.dto.AuthResponse;

public interface AuthService {
    AuthResponse verify(AuthRequest authRequest);
}
