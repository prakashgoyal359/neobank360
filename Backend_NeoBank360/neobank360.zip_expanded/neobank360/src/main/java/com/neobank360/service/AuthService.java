package com.neobank360.service;

import com.neobank360.dto.*;
import com.neobank360.entity.User;
import com.neobank360.repository.UserRepository;
import com.neobank360.util.GeneratorUtil;
import com.neobank360.security.JwtUtil;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired private UserRepository repo;
    @Autowired private PasswordEncoder encoder;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private OtpService otpService;

    public String register(RegisterRequest req) {

        if (!otpService.isVerified(req.getAadharNumber())) {
            throw new RuntimeException("Aadhaar not verified ❌");
        }

        String username;
        String accNo;
        
        // ACCOUNT TYPE VALIDATION (ADD HERE)
        List<String> allowed = List.of("SAVINGS", "CURRENT", "SALARY");

        if (!allowed.contains(req.getAccountType())) {
            throw new RuntimeException("Invalid Account Type ❌");
        }

        do {
            username = GeneratorUtil.generateUsername(req.getFirstName());
        } while (repo.findByUsername(username).isPresent());

        do {
            accNo = GeneratorUtil.generateAccountNumber();
        } while (repo.findByAccountNumber(accNo).isPresent());

        User user = User.builder()
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .gender(req.getGender())
                .email(req.getEmail())
                .address(req.getAddress())
                .accountType(req.getAccountType())
                .mobileNumber(req.getMobileNumber())
                .panNumber(req.getPanNumber())
                .aadharNumber(req.getAadharNumber())
                .username(username)
                .accountNumber(accNo)
                .balance(0)
                .password(encoder.encode(req.getPassword()))
                .role("CUSTOMER")
                .build();
        		
        repo.save(user);

        return "Username: " + username + "\nAccount: " + accNo;
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

        if (!encoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

        return Map.of(
            "token", token,
            "username", user.getUsername(),
            "role", user.getRole(),
            "accountType", user.getAccountType() // ✅ IMPORTANT
        );
    }
}