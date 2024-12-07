package com.example.userapp.mapper;

import com.example.userapp.dto.UserResponse;
import com.example.userapp.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserResponse userResponse) {
        if (userResponse == null) {
            return null;
        }

        User user = new User();
        user.setId(userResponse.getId());
        user.setEmail(userResponse.getEmail());
        user.setFirstName(userResponse.getFirstName());
        user.setMiddleName(userResponse.getMiddleName());
        user.setLastName(userResponse.getLastName());
        user.setRole(userResponse.getRole());
        user.setCustomerId(userResponse.getCustomerId());
        user.setDesignation(userResponse.getDesignation());
        user.setPhone(userResponse.getPhone());
        user.setStatusisEnabled(userResponse.isStatus());

        return user;
    }


    public UserResponse toDTO(User user) {
        if (user == null) {
            return null;
        }

        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setMiddleName(user.getMiddleName());
        userResponse.setLastName(user.getLastName());
        userResponse.setRole(user.getRole());
        userResponse.setCustomerId(user.getCustomerId());
        userResponse.setDesignation(user.getDesignation());
        userResponse.setPhone(user.getPhone());
        userResponse.setStatus(user.getStatusisEnabled());

        return userResponse;
    }
}
