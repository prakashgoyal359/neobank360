package com.neobank360.service;

import com.neobank360.dto.RegisterRequest;
import com.neobank360.entity.User;
import com.neobank360.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    public UserServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUserSuccess() {

        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@gmail.com");
        request.setPassword("Password@123");
        request.setFullName("Test User");

        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode(anyString()))
                .thenReturn("hashedPassword");

        User savedUser = new User();
        savedUser.setEmail(request.getEmail());

        when(userRepository.save(any(User.class)))
                .thenReturn(savedUser);

        var response = userService.register(request);

        assertNotNull(response);
        assertEquals("test@gmail.com", response.getEmail());
    }
}