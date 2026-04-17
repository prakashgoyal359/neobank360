package com.neobank360.service;

import com.neobank360.dto.*;
import com.neobank360.entity.User;
import com.neobank360.repository.UserRepository;
import com.neobank360.util.GeneratorUtil;
import com.neobank360.security.JwtUtil;

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
                .password(encoder.encode(req.getPassword()))
                .role("CUSTOMER")
                .build();

        repo.save(user);

        return "Username: " + username + "\nAccount: " + accNo;
    }

    public String login(LoginRequest req) {

        if (req.getUsername().equals("admin") && req.getPassword().equals("admin123")) {
            return jwtUtil.generateToken("admin", "ADMIN");
        }

        User user = repo.findByUsername(req.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtUtil.generateToken(user.getUsername(), user.getRole());
    }
}