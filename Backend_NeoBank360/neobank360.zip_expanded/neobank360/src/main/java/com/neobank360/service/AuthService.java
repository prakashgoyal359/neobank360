package com.neobank360.service;

import com.neobank360.dto.*;


import com.neobank360.entity.User;
import com.neobank360.repository.UserRepository;
import com.neobank360.security.JwtUtil;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class AuthService {

    @Autowired private UserRepository repo;
    @Autowired private PasswordEncoder encoder;
    @Autowired private JwtUtil jwtUtil;


    public String register(
            RegisterRequest req,
            MultipartFile aadharFile,
            MultipartFile panFile,
            MultipartFile photoFile
    ) throws Exception {

        User user = User.builder()
        		.password("PENDING")
                .firstName(req.getFirstName())
                .middleName(req.getMiddleName())
                .lastName(req.getLastName())
                		
                .gender(req.getGender())
                .address(req.getAddress())
                .email(req.getEmail())
                .aadharNumber(req.getAadharNumber())
                .panNumber(req.getPanNumber())

                .mobileNumber(req.getMobileNumber())
                .accountType(req.getAccountType())

                .role("CUSTOMER")
                .status("PENDING")
                .firstLogin(true)

                .aadharFile(aadharFile.getOriginalFilename())
                .panFile(panFile.getOriginalFilename())
                .photoFile(photoFile.getOriginalFilename())

                .build();

        repo.save(user);

        return "Account request submitted ✅";
    }
    public Map<String, Object> login(LoginRequest req) {

        // ✅ ADMIN LOGIN
        if (req.getUsername().equals("admin") && req.getPassword().equals("admin123")) {

            String token = jwtUtil.generateToken("admin", "ADMIN");

            return Map.of(
                "token", token,
                "username", "admin",
                "role", "ADMIN",
                "accountType", "ADMIN"
            );
        }

        // ✅ CUSTOMER LOGIN
        User user = repo.findByUsername(req.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (!user.getStatus().equals("APPROVED")) {
            throw new RuntimeException("Account not approved ❌");
        }

        if (!encoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

        return Map.of(
            "token", token,
            "firstLogin", user.getFirstLogin(),
            "username", user.getUsername(),
            "role", user.getRole(),
            "accountType", user.getAccountType() // ✅ IMPORTANT
        );
    }
    
    public String changePassword(
            String username,
            String oldPassword,
            String newPassword
    ) {

        User user = repo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Old password incorrect ❌");
        }

        user.setPassword(
                encoder.encode(newPassword)
        );

        user.setFirstLogin(false);

        repo.save(user);

        return "Password updated ✅";
    }
    
    
}