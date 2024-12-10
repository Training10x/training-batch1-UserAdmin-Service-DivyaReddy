package com.example.userapp.controller;

import com.example.userapp.dto.*;
import com.example.userapp.entity.User;
import com.example.userapp.service.impl.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import java.util.Collections;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@EnableWebMvc
class UserControllerTest {

    private UserService userService;
    private UserController userController;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = mock(UserService.class); // Mocking the service
        userController = new UserController(userService); // Injecting the mocked service

        mvc = MockMvcBuilders.standaloneSetup(userController)
                .setHandlerExceptionResolvers(new ExceptionHandlerExceptionResolver()).build();
    }

    @Test
    void testHello() {
        String response = userController.hello();
        assertEquals("Hello World!", response, "The hello endpoint should return 'Hello World!'");
    }

    @Test
    void testCreateUser() {
        User user = new User();
        user.setId(1L);
        when(userService.createUser(any(User.class))).thenReturn(user);

        ResponseEntity<Long> response = userController.createUser(user);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(1L, response.getBody());
        verify(userService, times(1)).createUser(any(User.class));
    }

    @Test
    void testUpdateStatus() {
        StatusResponse status = new StatusResponse();
        status.setStatus("enabled");

        UserResponse mockResponse = new UserResponse();
        mockResponse.setId(1L);

        when(userService.updateUserStatus(anyLong(), any(StatusResponse.class))).thenReturn(mockResponse);

        ResponseEntity<UserResponse> response = userController.updateStatus(1L, status);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
        verify(userService, times(1)).updateUserStatus(anyLong(), any(StatusResponse.class));
    }

    @Test
    void testUpdateUser() {
        UpdateDetails updateDetails = new UpdateDetails();
        updateDetails.setEmail("updated@example.com");

        UserResponse mockResponse = new UserResponse();
        mockResponse.setId(1L);

        when(userService.updateUser(anyLong(), any(UpdateDetails.class))).thenReturn(mockResponse);

        ResponseEntity<UserResponse> response = userController.updateUser(1L, updateDetails);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
        verify(userService, times(1)).updateUser(anyLong(), any(UpdateDetails.class));
    }

    @Test
    void testRetrieveUsers() {
        SearchResponse mockResponse = SearchResponse.builder()
                .users(Collections.emptyList())
                .currentPage(0)
                .pageCount(1)
                .totalCount(0L)
                .build();

        when(userService.retrieveUsers(any(), any(), any(), any(), any(), any())).thenReturn(mockResponse);

        ResponseEntity<SearchResponse> response = userController.retrieveUsers(0, 10, "John", "Doe", "1234567890", "admin");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(0, response.getBody().getUsers().size());
        verify(userService, times(1)).retrieveUsers(any(), any(), any(), any(), any(), any());
    }

    @Test
    public void testException() throws Exception {
        when(userService.retrieveUsers(any(), any(), any(), any(), any(), any())).thenThrow(new RuntimeException("test error"));

        mvc.perform(get("/api/v1/users")).andExpect(status().isBadRequest());
    }

    @Test
    public void testException1() throws Exception {

        when(userService.retrieveUsers(any(), any(), any(), any(), any(), any())).thenThrow(new RuntimeException("duplicate key value violates unique constraint \"users_email_key\""));

        mvc.perform(get("/api/v1/users")).andExpect(status().isConflict());
    }

    @Test
    public void testException2() throws Exception {

        when(userService.retrieveUsers(any(), any(), any(), any(), any(), any())).thenThrow(new RuntimeException("duplicate key value violates unique constraint \"users_phone_key\""));

        mvc.perform(get("/api/v1/users")).andExpect(status().isConflict());
    }

}
