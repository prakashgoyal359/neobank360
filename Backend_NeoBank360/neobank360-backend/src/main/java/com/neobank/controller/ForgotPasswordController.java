package com.neobank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neobank.service.ForgotPasswordService;

@RestController
@RequestMapping("/api/auth")
public class ForgotPasswordController {

    @Autowired private ForgotPasswordService service;

    @PostMapping("/forgot/send")
    public void send(@RequestParam String email){
        service.sendOtp(email);
    }

    @PostMapping("/forgot/reset")
    public void reset(@RequestParam String email,
                      @RequestParam String otp,
                      @RequestParam String pass){
        service.reset(email,otp,pass);
    }
}