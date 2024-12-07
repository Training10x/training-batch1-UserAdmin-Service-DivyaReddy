package com.example.userapp.dto;

import lombok.Data;

@Data
public class UserResponse {

    private Long id;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String role;
    private String customerId;
    private String designation;
    private String phone;
    private boolean status;

}
