package com.neobank360.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.neobank360.entity.User;
import com.neobank360.service.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

	private final AdminService service;
    @GetMapping("/dashboard")
    public String admin() {
        return "Welcome Admin";
    }
    
    // ✅ GET ALL USERS
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    // ✅ DELETE USER
    @DeleteMapping("/user/{id}")
    public Map<String, String> deleteUser(@PathVariable Long id) {
        return Map.of("message", service.deleteUser(id));
    }

    // ✅ UPDATE USER
    @PutMapping("/user/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        return service.updateUser(id, updatedUser);
    }

    // ✅ CREATE USER (optional)
    @PostMapping("/user")
    public User createUser(@RequestBody User user) {
        return service.createUser(user);
    }
    
    @PutMapping("/approve/{id}")
    public Map<String, String> approve(
            @PathVariable Long id,
            @RequestBody Map<String, String> req
    ) {

        String username = service.approveUser(
                id,
                req.get("username"),
                req.get("password")
        );

        return Map.of(
                "message", "Approved ✅",
                "username", username
        );
    }
    
    @PutMapping("/reject/{id}")
    public Map<String, String> reject(
            @PathVariable Long id
    ) {

        return Map.of(
                "message",
                service.rejectUser(id)
        );
    }
}