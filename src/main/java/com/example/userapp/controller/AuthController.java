package com.example.userapp.controller;


import com.example.userapp.dto.AuthRequest;
import com.example.userapp.dto.AuthResponse;
import com.example.userapp.service.impl.AuthServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthServiceImpl authService;

    public AuthController(AuthServiceImpl authService) {
        this.authService=authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        AuthResponse token = authService.verify(authRequest);
        return ResponseEntity.ok(token);

    }

}

