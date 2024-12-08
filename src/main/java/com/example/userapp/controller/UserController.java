package com.example.userapp.controller;

import com.example.userapp.dto.SearchResponse;
import com.example.userapp.dto.StatusResponse;
import com.example.userapp.dto.UpdateDetails;
import com.example.userapp.dto.UserResponse;
import com.example.userapp.entity.User;
import com.example.userapp.service.impl.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    // Constructor Injection
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("welcome")
    public String hello(){
        return "Hello World!";
    }

    @PostMapping("/api/v1/users")
    public ResponseEntity<Long> createUser(@RequestBody @Valid User user) {
        User savedUser = userService.createUser(user);
        logger.info("User created: {}", user);
        return new ResponseEntity<>(savedUser.getId(), HttpStatus.CREATED);
    }

    @PatchMapping("/api/v1/users/{id}")
    public ResponseEntity<UserResponse> updateStatus(@PathVariable("id") Long userId, @RequestBody StatusResponse status) {
        UserResponse user = userService.updateUserStatus(userId, status);

        return ResponseEntity.ok(user);
    }

    @PutMapping("/api/v1/users/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable("id") long id, @RequestBody UpdateDetails updateDetails) {
        UserResponse user = userService.updateUser(id,updateDetails);
        return ResponseEntity.ok(user);
    }


    @GetMapping("/api/v1/users")
    public ResponseEntity<SearchResponse> retrieveUsers(@RequestParam(required = false) Integer page,
                                                        @RequestParam(required = false) Integer limit,
                                                        @RequestParam(required = false) String firstName,
                                                        @RequestParam(required = false) String lastName,
                                                       // @RequestParam(required = false) String email,
                                                        @RequestParam(required = false) String phone,
                                                       // @RequestParam(required = false) String middleName,
                                                        //@RequestParam(required = false) String designation,
                                                        @RequestParam(required = false) String role)
                                                        //@RequestParam(required = false) String companyId)
    {
        SearchResponse searchResponse = userService.retrieveUsers(page, limit, firstName, lastName, phone, role);
        return ResponseEntity.ok(searchResponse);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleExceptions(Exception ex) {
        if (ex.getMessage().contains("duplicate key value violates unique constraint \"users_email_key\"")) {
            return new ResponseEntity<>("Email ID already exists", HttpStatus.CONFLICT);
        } else if (ex.getMessage().contains("duplicate key value violates unique constraint \"users_phone_key\"")) {
            return new ResponseEntity<>("Phone number already exists", HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>("Some error occurred", HttpStatus.BAD_REQUEST);
        }
    }
}
