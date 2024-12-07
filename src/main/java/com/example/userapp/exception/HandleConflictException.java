package com.example.userapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class HandleConflictException {

        @ExceptionHandler(ConflictException.class)
        public ResponseEntity<Map<String, Object>> ConflictExceptionHandler(ConflictException ex) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", HttpStatus.CONFLICT.value());
            response.put("error", "Conflict");
            response.put("message", ex.getMessage());

            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);

    }
}
