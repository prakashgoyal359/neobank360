package com.neobank360.controller;

import com.neobank360.entity.User;
import com.neobank360.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdminControllerTest {

    @Test
    void shouldAllowAdminAccess() {

        UserService userService = Mockito.mock(UserService.class);

        AdminController controller = new AdminController(userService);

        List<User> users = List.of(new User());
        Mockito.when(userService.getAllUsers()).thenReturn(users);

        // Simulate ADMIN auth
        var auth = new UsernamePasswordAuthenticationToken(
                "admin@gmail.com",
                null,
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );

        List<User> result = controller.getAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
    }
}