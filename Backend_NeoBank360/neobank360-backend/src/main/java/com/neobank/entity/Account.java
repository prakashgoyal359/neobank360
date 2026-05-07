package com.neobank.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class Account {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String accountNumber;
    private double balance;
}