package com.neobank360.controller;

import com.neobank360.dto.LoginRequest;
import com.neobank360.dto.RegisterRequest;
import com.neobank360.dto.RegisterResponse;
import com.neobank360.service.UserService;
import com.neobank360.config.JwtUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ✅ Register Test
    @Test
    void testRegisterSuccess() {

        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@gmail.com");
        request.setPassword("Password@123");
        request.setFullName("Test User");

        // 🔥 FIX: use mock instead of new constructor
        RegisterResponse response = mock(RegisterResponse.class);

        when(userService.register(request)).thenReturn(response);

        ResponseEntity<?> result = authController.register(request);

        assertEquals(201, result.getStatusCode().value()); // ✅ FIX
        assertNotNull(result.getBody());
    }

    // ✅ Register Failure
    @Test
    void testRegisterFailure() {

        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@gmail.com");

        when(userService.register(request))
                .thenThrow(new RuntimeException("Email exists"));

        ResponseEntity<?> result = authController.register(request);

        assertEquals(409, result.getStatusCode().value()); // ✅ FIX
    }

    // ✅ Login Test (UPDATED)
    @Test
    void testLoginSuccess() {

        LoginRequest request = new LoginRequest();
        request.setEmail("test@gmail.com");
        request.setPassword("Password@123");

        when(jwtUtil.generateToken(request.getEmail()))
                .thenReturn("mock-token");

        ResponseEntity<?> response = authController.login(request);

        assertEquals(200, response.getStatusCode().value());

        // 🔥 Extract token from Map
        Map<?, ?> body = (Map<?, ?>) response.getBody();

        assertNotNull(body);
        assertEquals("mock-token", body.get("token"));
    }
}