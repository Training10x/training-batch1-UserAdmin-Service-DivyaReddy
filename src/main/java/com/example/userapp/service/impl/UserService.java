package com.example.userapp.service.impl;

import com.example.userapp.dto.SearchResponse;
import com.example.userapp.dto.StatusResponse;
import com.example.userapp.dto.UpdateDetails;
import com.example.userapp.dto.UserResponse;
import com.example.userapp.entity.User;

public interface UserService {
    User createUser(User user);

    UserResponse updateUserStatus(Long userId, StatusResponse status);

    public UserResponse updateUser(Long userId, UpdateDetails updateUser);

    SearchResponse retrieveUsers(Integer page, Integer limit, String firstName, String lastName, String phone, String role);
}
