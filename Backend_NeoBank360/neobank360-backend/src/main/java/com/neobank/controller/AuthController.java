package com.neobank.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.neobank.service.*;
import com.neobank.security.*;
import com.neobank.entity.*;
import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private AuthService service;
    @Autowired private JwtUtil jwt;

    @PostMapping("/register")
    public User register(@RequestBody User u){
        return service.register(u);
    }

    @PostMapping("/login")
    public Map<String,String> login(@RequestBody User u){

        User user = service.login(u.getEmail(),u.getPassword());

        String token = jwt.generateToken(user.getEmail());

        return Map.of("token",token);
    }
}