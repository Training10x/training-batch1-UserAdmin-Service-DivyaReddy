package com.example.userapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    @NotBlank(message = "Firstname is mandatory")
    private String firstName;
    private String middleName;
    @NotBlank(message = "Lastname is mandatory")
    private String lastName;
    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password should be 8 characters minimum")
    private String password;
    @NotBlank(message = "Role is mandatory")
    private String role;
    @NotBlank(message = "Customer Id is mandatory")
    private String customerId;
    private String designation;
    @Column(unique = true)
    private String phone;
    private Boolean statusisEnabled;
}

