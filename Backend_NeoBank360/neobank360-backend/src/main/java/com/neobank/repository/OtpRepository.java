package com.neobank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neobank.entity.Otp;

public interface OtpRepository extends JpaRepository<Otp, Long> {
    Otp findByEmail(String email);
}