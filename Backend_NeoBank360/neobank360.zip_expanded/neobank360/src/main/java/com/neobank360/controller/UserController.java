package com.neobank360.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.neobank360.entity.User;
import com.neobank360.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/me")
    public User getProfile(Authentication auth) {
        return userService.getUserByEmail(auth.getName());
    }

    @PutMapping("/me")
    public User updateProfile(@RequestBody User updatedUser, Authentication auth) {
        return userService.updateUser(auth.getName(), updatedUser);
    }
}