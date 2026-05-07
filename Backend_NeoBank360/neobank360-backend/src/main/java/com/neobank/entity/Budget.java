package com.neobank.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class Budget {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String category;
    private double limitAmount;
}