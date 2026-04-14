package com.neobank360.controller;

import com.neobank360.entity.User;
import com.neobank360.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    @Test
    void shouldGetUserProfile() {

        UserService userService = Mockito.mock(UserService.class);

        User user = new User();
        user.setEmail("test@gmail.com");

        Mockito.when(userService.getUserByEmail("test@gmail.com"))
                .thenReturn(user);

        UserController controller = new UserController(userService);

        var auth = new UsernamePasswordAuthenticationToken("test@gmail.com", null);

        User result = controller.getProfile(auth);

        assertEquals("test@gmail.com", result.getEmail());
    }

    @Test
    void shouldUpdateUserProfile() {

        UserService userService = Mockito.mock(UserService.class);

        User updated = new User();
        updated.setFullName("Updated Name");

        Mockito.when(userService.updateUser(Mockito.anyString(), Mockito.any()))
                .thenReturn(updated);

        UserController controller = new UserController(userService);

        var auth = new UsernamePasswordAuthenticationToken("test@gmail.com", null);

        User result = controller.updateProfile(updated, auth);

        assertEquals("Updated Name", result.getFullName());
    }
}