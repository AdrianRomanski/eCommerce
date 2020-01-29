package com.example.controllers;

import com.example.demo.TestUtils;
import com.example.model.persistence.User;
import com.example.model.persistence.repositories.CartRepository;
import com.example.model.persistence.repositories.UserRepository;
import com.example.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.apache.commons.lang3.RandomStringUtils;


import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest extends CreationHelper {

    private UserController userController;
    private UserRepository userRepository = mock(UserRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setUp() {
        userController = new UserController();
        TestUtils.injectObjects(userController, "userRepository", userRepository);
        TestUtils.injectObjects(userController, "cartRepository", cartRepository);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder", encoder);
    }

    @Test
    public void validateFindByUsernamePositiveScenario() {
        when(userRepository.findByUsername("Adrian")).thenReturn(createUser());
        final ResponseEntity<User> response = userController.findByUserName("Adrian");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        User responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("Adrian", responseBody.getUsername());
    }

    @Test
    public void validateFindByUserNameNegativeScenario() {
        final ResponseEntity<User> response = userController.findByUserName("Sebastian");
        assertEquals(404, response.getStatusCodeValue());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void validateFindByIdPositiveScenario() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(createUser()));
        final ResponseEntity<User> response = userController.findById(1L);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        User responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("Adrian", responseBody.getUsername());
        assertEquals("AdrianAdrian", responseBody.getPassword());
    }

    @Test
    public void validateFindByIdNegativeScenario() {
        final ResponseEntity<User> response = userController.findById(155L);
        assertEquals(404, response.getStatusCodeValue());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    public void createUserHappyPath() {
        when(encoder.encode("AdrianAdrian")).thenReturn("thisIsHashed");
        CreateUserRequest userRequest = createUserRequest();
        final ResponseEntity<User> response = userController.createUser(userRequest);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        User responseBodyUser = response.getBody();
        assertNotNull(responseBodyUser);
        assertEquals(0, responseBodyUser.getId());
        assertEquals("Adrian", responseBodyUser.getUsername());
        assertEquals("thisIsHashed", responseBodyUser.getPassword());
    }

    @Test
    public void createUserNotHappyPath() {
        when(encoder.encode("Adrian")).thenReturn("thisIsHashed");
        CreateUserRequest userRequest = createUserRequestNegativeScenario();
        final ResponseEntity<User> response = userController.createUser(userRequest);
        assertEquals(400, response.getStatusCodeValue());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void createUserNotHappyPath2() {
        when(encoder.encode("")).thenReturn("thisIsHashed");
        CreateUserRequest userRequest = createUserRequestNegativeScenario2();
        final ResponseEntity<User> response = userController.createUser(userRequest);
        assertEquals(400, response.getStatusCodeValue());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void createUserNotHappyPath3() {
        when(encoder.encode("Adrian1234!")).thenReturn("thisIsHashed");
        CreateUserRequest userRequest = createUserRequestNegativeScenario3();
        final ResponseEntity<User> response = userController.createUser(userRequest);
        assertEquals(400, response.getStatusCodeValue());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}