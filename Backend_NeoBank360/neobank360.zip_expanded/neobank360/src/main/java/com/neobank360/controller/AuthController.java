package com.neobank360.controller;

import com.neobank360.dto.*;
import com.neobank360.service.*;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired private AuthService authService;
    @Autowired private OtpService otpService;

    @PostMapping("/otp/send")
    public Map<String, Object> sendOtp(@RequestParam String aadhar) {

        int otp = otpService.generateOtp(aadhar); // 👈 return OTP

        return Map.of(
            "message", "OTP sent",
            "otp", otp // ✅ send to frontend (for testing only)
        );
    }

    @PostMapping("/otp/verify")
    public Map<String, String> verifyOtp(@RequestParam String aadhar, @RequestParam int otp) {

        boolean result = otpService.verifyOtp(aadhar, otp);

        return Map.of("message", result ? "Verified" : "Invalid OTP");
    }

    @PostMapping("/auth/register")
    public Map<String, String> register(@RequestBody RegisterRequest req) {

        String result = authService.register(req);

        // split result into username + account
        String[] parts = result.split("\n");

        String username = parts[0].split(": ")[1];
        String account = parts[1].split(": ")[1];

        return Map.of(
            "message", "Account Created Successfully",
            "username", username,
            "accountNumber", account
        );
    }

    @PostMapping("/auth/login")
    public Map<String, String> login(@RequestBody LoginRequest req) {

        String token = authService.login(req);

        return Map.of(
            "token", token
        );
    }
}