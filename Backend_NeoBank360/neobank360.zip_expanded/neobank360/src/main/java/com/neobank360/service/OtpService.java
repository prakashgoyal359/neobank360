package com.neobank360.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OtpService {

    private Map<String, Integer> otpMap = new HashMap<>();
    private Set<String> verified = new HashSet<>();

    public int generateOtp(String aadhar) {
        int otp = new Random().nextInt(9000) + 1000;

        otpMap.put(aadhar, otp);

        System.out.println("AADHAR OTP: " + otp); // backend log

        return otp; // ✅ return OTP
    }

    public boolean verifyOtp(String aadhar, int otp) {
        if (otpMap.containsKey(aadhar) && otpMap.get(aadhar) == otp) {
            verified.add(aadhar);
            otpMap.remove(aadhar);
            return true;
        }
        return false;
    }

    public boolean isVerified(String aadhar) {
        return verified.contains(aadhar);
    }
}