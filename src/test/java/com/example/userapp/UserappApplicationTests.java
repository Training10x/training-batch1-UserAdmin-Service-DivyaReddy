package com.example.userapp.service;

import com.example.userapp.dto.*;
import com.example.userapp.entity.User;
import com.example.userapp.exception.NotFoundException;
import com.example.userapp.mapper.UserMapper;
import com.example.userapp.repository.UserRepository;
import com.example.userapp.service.impl.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

	private UserRepository userRepository;
	private UserMapper userMapper;
	private UserServiceImpl userService;

	@BeforeEach
	void setUp() {
		userRepository = mock(UserRepository.class);
		userMapper = mock(UserMapper.class);
		userService = new UserServiceImpl(userRepository, userMapper);
	}

	@Test
	void testCreateUser() {
		User user = new User();
		user.setPassword("plainPassword");
		User savedUser = new User();
		savedUser.setId(1L);

		when(userRepository.save(any(User.class))).thenReturn(savedUser);

		User result = userService.createUser(user);

		assertNotNull(result);
		assertEquals(1L, result.getId());
		verify(userRepository, times(1)).save(any(User.class));
	}

	@Test
	void testUpdateUserStatus_UserFound() {
		User user = new User();
		user.setId(1L);
		user.setStatusisEnabled(false);
		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		when(userRepository.save(any(User.class))).thenReturn(user);

		StatusResponse status = new StatusResponse();
		status.setStatus("enabled");

		UserResponse userResponse = new UserResponse();
		when(userMapper.toDTO(any(User.class))).thenReturn(userResponse);

		UserResponse result = userService.updateUserStatus(1L, status);

		assertNotNull(result);
		assertTrue(user.getStatusisEnabled());
		verify(userRepository, times(1)).save(user);
	}

	@Test
	void testUpdateUserStatus_UserNotFound() {
		when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

		StatusResponse status = new StatusResponse();
		status.setStatus("enabled");

		assertThrows(NotFoundException.class, () -> userService.updateUserStatus(1L, status));
	}

	@Test
	void testUpdateUser_UserFound() {
		User user = new User();
		user.setId(1L);
		user.setEmail("old@example.com");


		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		when(userRepository.save(any(User.class))).thenReturn(user);

		UpdateDetails updateDetails = new UpdateDetails();
		updateDetails.setEmail("new@example.com");
		updateDetails.setFirstName("Avjhv");
		updateDetails.setLastName("hvjvk");
		updateDetails.setMiddleName("nvjvb");
		updateDetails.setPhone("8909382");
		updateDetails.setDesignation("Software");
		updateDetails.setRole("Sofware");
		updateDetails.setCustomerId("78");

		UserResponse userResponse = new UserResponse();
		when(userMapper.toDTO(any(User.class))).thenReturn(userResponse);

		UserResponse result = userService.updateUser(1L, updateDetails);

		assertNotNull(result);
		assertEquals("new@example.com", user.getEmail());
		verify(userRepository, times(1)).save(user);
	}

	@Test
	void testUpdateUser_UserNotFound() {
		when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

		UpdateDetails updateDetails = new UpdateDetails();
		updateDetails.setEmail("new@example.com");

		assertThrows(NotFoundException.class, () -> userService.updateUser(1L, updateDetails));
	}

	@Test
	void testRetrieveUsers() {
		User user = new User();
		user.setId(1L);
		user.setFirstName("John");
		Page<User> userPage = new PageImpl<>(Collections.singletonList(user));

		when(userRepository.findByFirstNameContainingAndLastNameContainingAndPhoneContainingAndRoleContaining(
				anyString(), anyString(), anyString(), anyString(), any(PageRequest.class)
		)).thenReturn(userPage);

		UserResponse userResponse = new UserResponse();
		userResponse.setId(1L);
		when(userMapper.toDTO(any(User.class))).thenReturn(userResponse);

		SearchResponse result = userService.retrieveUsers(0, 10, "John", "Doe", "1234567890", "admin");

		assertNotNull(result);
		assertEquals(1, result.getUsers().size());
		assertEquals(1L, result.getUsers().get(0).getId());
	}

	@Test
	void testUpdateUserStatus_UserNotFoundInDisabled() {
		when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

		StatusResponse status = new StatusResponse();
		status.setStatus("disabled");

		assertThrows(NotFoundException.class, () -> userService.updateUserStatus(5L, status));
	}

	@Test
	void testUpdateUserStatus_UserFoundStatusDisabled() {
		User user = new User();
		user.setId(1L);
		user.setStatusisEnabled(false);
		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		when(userRepository.save(any(User.class))).thenReturn(user);

		StatusResponse status = new StatusResponse();
		status.setStatus("disabled");

		UserResponse userResponse = new UserResponse();
		when(userMapper.toDTO(any(User.class))).thenReturn(userResponse);

		UserResponse result = userService.updateUserStatus(1L, status);

		assertNotNull(result);
		assertFalse(user.getStatusisEnabled());
		verify(userRepository, times(1)).save(user);
	}
}
