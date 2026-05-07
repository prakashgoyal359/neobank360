package com.neobank.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@Entity
@Data
public class Bill {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String billerName;
    private double amount;
    private LocalDate dueDate;
    private String status;
}