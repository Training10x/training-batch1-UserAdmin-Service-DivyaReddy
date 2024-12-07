package com.example.userapp.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class StatusResponse {

    @Pattern(regexp="enabled|disabled")
    private String status;
}
