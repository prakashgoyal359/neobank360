package com.neobank360.service;

import com.neobank360.dto.RegisterRequest;
import com.neobank360.dto.RegisterResponse;
import com.neobank360.entity.Role;
import com.neobank360.entity.User;
import com.neobank360.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
   

	private final PasswordEncoder passwordEncoder;

    public RegisterResponse register(RegisterRequest request) {

        // Check email uniqueness
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Create user
        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.CUSTOMER)              // ✅ FIX
                .isActive(true)                   // ✅ FIX
                .createdAt(LocalDateTime.now())   // ✅ FIX
                .build();

        // Save user
        User savedUser = userRepository.save(user);

        // Return response
        return RegisterResponse.builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .fullName(savedUser.getFullName())
                .message("User registered successfully")
                .build();
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    public User updateUser(String email, User updatedUser) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ✅ Update fields
        user.setFullName(updatedUser.getFullName());
        user.setEmail(updatedUser.getEmail());

        // ⚠️ Optional: update password (only if provided)
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        return userRepository.save(user);
    }
    
}