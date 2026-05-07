package com.neobank.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true)
    private String email;

    private String password;

    private String role;

    private int failedAttempts;
    private boolean accountLocked;
}