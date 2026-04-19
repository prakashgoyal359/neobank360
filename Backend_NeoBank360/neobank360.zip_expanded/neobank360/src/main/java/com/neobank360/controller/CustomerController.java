package com.neobank360.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.neobank360.entity.User;
import com.neobank360.repository.UserRepository;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

	@Autowired
    private UserRepository repo;

    @GetMapping("/me")
    public Map<String, Object> getUser(Principal principal) {

        User user = repo.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return Map.of(
            "username", user.getUsername(),
            "accountType", user.getAccountType(),
            "accountNumber", user.getAccountNumber(),
            "balance", user.getBalance()
        );
    }
}