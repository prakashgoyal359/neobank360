package com.neobank.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@Entity
@Data
public class Otp {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String otp;
    private LocalDateTime expiry;
}