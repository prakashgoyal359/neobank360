package com.neobank360.controller;

import com.neobank360.dto.*;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import com.neobank360.service.*;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired 
    private AuthService authService;

    @Autowired 
    private OtpService otpService;

    // ================= OTP =================

    @PostMapping("/otp/send")
    public Map<String, Object> sendOtp(@RequestParam String aadhar) {

        int otp = otpService.generateOtp(aadhar);

        return Map.of(
            "message", "OTP sent",
            "otp", otp // ⚠️ only for testing
        );
    }

    @PostMapping("/otp/verify")
    public Map<String, String> verifyOtp(
            @RequestParam String aadhar,
            @RequestParam(required = false) Integer otp) {

        if (otp == null) {
            throw new RuntimeException("OTP required ❌");
        }

        boolean result = otpService.verifyOtp(aadhar, otp);

        return Map.of("message", result ? "Verified" : "Invalid OTP");
    }

    // ================= REGISTER =================

    @PostMapping(
            value = "/auth/register",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public Map<String, String> register(

            @RequestPart("data") RegisterRequest req,

            @RequestPart("aadharFile") MultipartFile aadharFile,

            @RequestPart("panFile") MultipartFile panFile,

            @RequestPart("photoFile") MultipartFile photoFile

    ) throws Exception {

        return Map.of(
                "message",
                authService.register(
                        req,
                        aadharFile,
                        panFile,
                        photoFile
                )
        );
    }

    // ================= LOGIN =================

    @PostMapping("/auth/login")
    public Map<String, Object> login(@RequestBody LoginRequest req) {

        return authService.login(req);
    }
    
    @PutMapping("/auth/change-password")
    public Map<String, String> changePassword(

            @RequestParam String username,
            @RequestParam String oldPassword,
            @RequestParam String newPassword
    ) {

        return Map.of(
                "message",
                authService.changePassword(
                        username,
                        oldPassword,
                        newPassword
                )
        );
    }
    
}